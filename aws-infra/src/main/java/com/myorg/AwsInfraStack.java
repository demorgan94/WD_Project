package com.myorg;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Instance;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.KeyPair;
import software.amazon.awscdk.services.ec2.MachineImage;
import software.amazon.awscdk.services.ec2.Peer;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.UserData;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecr.LifecycleRule;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.iam.Effect;
import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.rds.Credentials;
import software.amazon.awscdk.services.rds.DatabaseInstance;
import software.amazon.awscdk.services.rds.DatabaseInstanceEngine;
import software.amazon.awscdk.services.rds.StorageType;
import software.constructs.Construct;

import java.util.List;

public class AwsInfraStack extends Stack {
    public AwsInfraStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public AwsInfraStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // Definition of a VPC network
        Vpc vpc = Vpc.Builder.create(this, "WDVpc")
                .maxAzs(2)
                .build();

        // Create an ECR repository
        Repository ecrRepository = Repository.Builder.create(this, "WDEcrRepository")
                .repositoryName("wd-backend-repository")
                .removalPolicy(RemovalPolicy.DESTROY)  // Deletes the repository when the stack is destroyed (optional)
                .build();

        // Define ECR lifecycle policy
        ecrRepository.addLifecycleRule(LifecycleRule.builder()
                .rulePriority(1)
                .description("Retain last 5 images")
                .maxImageCount(5)  // Retain the last 5 images
                .build());

        // Output the ECR repository URI
        CfnOutput.Builder.create(this, "WDBackendEcrRepoUri")
                .value(ecrRepository.getRepositoryUri())
                .description("URI of backend ECR repository")
                .build();

        // Define Security Group for the EC2 instance to allow SSH and HTTP access
        SecurityGroup ec2SecurityGroup = SecurityGroup.Builder.create(this, "WDEc2SecurityGroup")
                .vpc(vpc)
                .allowAllOutbound(true)
                .build();
        ec2SecurityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(22), "Allow SSH");
        ec2SecurityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(80), "Allow HTTP");
        ec2SecurityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(443), "Allow HTTPS");

        // Create IAM Role for EC2 instance
        Role ec2Role = Role.Builder.create(this, "WDEc2InstanceRole")
                .assumedBy(new ServicePrincipal("ec2.amazonaws.com"))
                .managedPolicies(List.of(
                        ManagedPolicy.fromAwsManagedPolicyName("AmazonEC2FullAccess"),
                        ManagedPolicy.fromAwsManagedPolicyName("AmazonEC2ContainerRegistryReadOnly"),
                        ManagedPolicy.fromAwsManagedPolicyName("AmazonS3ReadOnlyAccess")
                ))
                .build();

        // Attach policy to allow pulling images from ECR
        ec2Role.addToPolicy(PolicyStatement.Builder.create()
                .actions(List.of("ecr:GetAuthorizationToken", "ecr:BatchGetImage", "ecr:GetDownloadUrlForLayer"))
                .resources(List.of(ecrRepository.getRepositoryArn()))
                .effect(Effect.ALLOW)
                .build());

        // Commands to install and run Docker on EC2
        UserData userData = UserData.forLinux();
        userData.addCommands(
                "sudo yum update -y",
                "sudo yum install docker -y",
                "sudo service docker start",
                "sudo systemctl enable docker",
                "sudo usermod -aG docker ec2-user",
                "sudo systemctl restart docker"
        );

        // EC2 instance setup
        Instance ec2Instance = Instance.Builder.create(this, "WDEc2Instance")
                .vpc(vpc)
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO)) // Free tier eligible
                .machineImage(MachineImage.latestAmazonLinux2()) // Free tier eligible
                .securityGroup(ec2SecurityGroup)
                .role(ec2Role)
                .keyPair(KeyPair.fromKeyPairName(this, "WDEc2KeyPair", "wd-ec2-key-pair"))
                .associatePublicIpAddress(true)
                .vpcSubnets(SubnetSelection.builder().subnetType(SubnetType.PUBLIC).build())
                .userData(userData)
                .build();

        // Output the EC2 instance's public DNS
        CfnOutput.Builder.create(this, "WDEc2InstancePublicDns")
                .value(ec2Instance.getInstancePublicDnsName())
                .description("Public DNS of the EC2 instance")
                .build();

        // Output the EC2 instance's public IP
        CfnOutput.Builder.create(this, "WDEc2InstancePublicIp")
                .value(ec2Instance.getInstancePublicIp())
                .description("Public IP of the EC2 instance")
                .build();

        // Define Security Group for the RDS instance to allow MySQL connections from the EC2 instance
        SecurityGroup rdsSecurityGroup = SecurityGroup.Builder.create(this, "WDRdsSecurityGroup")
                .vpc(vpc)
                .allowAllOutbound(true)
                .build();
        rdsSecurityGroup.addIngressRule(ec2SecurityGroup, Port.tcp(3306), "Allow MySQL access from EC2 instance");

        // RDS database setup
        DatabaseInstance rdsInstance = DatabaseInstance.Builder.create(this, "WDRdsInstance")
                .engine(DatabaseInstanceEngine.MYSQL)
                .instanceType(InstanceType.of(InstanceClass.T4G, InstanceSize.MICRO))  // Free Tier eligible
                .vpc(vpc)
                .securityGroups(List.of(rdsSecurityGroup))
                .multiAz(false)  // Free Tier eligible (single-AZ)
                .allocatedStorage(20)  // Free Tier includes 20GB of storage
                .storageType(StorageType.GP2)
                .credentials(Credentials.fromGeneratedSecret("dbAdmin"))  // Auto-generate a password
                .databaseName("wddb")
                .publiclyAccessible(false)  // Only accessible from within VPC (for security)
                .instanceIdentifier("wd-rds-instance")
                .build();

        rdsInstance.getConnections().allowDefaultPortFrom(ec2Instance.getConnections(), "Allow EC2 to connect to RDS");

        // Output the RDS instance endpoint
        CfnOutput.Builder.create(this, "RdsInstanceEndpoint")
                .value(rdsInstance.getDbInstanceEndpointAddress())
                .description("Endpoint of the RDS MySQL instance")
                .build();
    }
}
