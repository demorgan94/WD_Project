package com.myorg;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.FargateService;
import software.amazon.awscdk.services.ecs.FargateTaskDefinition;
import software.amazon.awscdk.services.rds.DatabaseInstance;
import software.amazon.awscdk.services.rds.DatabaseInstanceEngine;
import software.amazon.awscdk.services.rds.StorageType;
import software.constructs.Construct;

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

        // EC2 instance setup
        Instance ec2Instance = Instance.Builder.create(this, "WDEc2Instance")
                .vpc(vpc)
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO)) // Free tier eligible
                .machineImage(MachineImage.latestAmazonLinux2()) // Free tier eligible
                .build();

        // RDS database setup
        DatabaseInstance rdsInstance = DatabaseInstance.Builder.create(this, "WDRdsInstance")
                .engine(DatabaseInstanceEngine.MYSQL) // MySQL engine
                .vpc(vpc)
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO)) // Free tier eligible
                .databaseName("wd-db")
                .allocatedStorage(20) // Free tier includes 20GB of storage
                .multiAz(false) // Single-AZ deployment is free tier eligible
                .publiclyAccessible(true) // Allows access from the EC2 instance and public internet
                .storageType(StorageType.GP2)
                .build();

        // Output the EC2 instance public DNS
        CfnOutput.Builder.create(this, "WdEc2PublicDns")
                .value(ec2Instance.getInstancePublicDnsName())
                .description("Public DNS of EC2 instance")
                .build();

        // Create an ECR repository for backend
        Repository ecrRepo = Repository.Builder.create(this, "WDBackendEcrRepo")
                .repositoryName("wd-backend")
                .build();

        // Output the ECR repository URI
        CfnOutput.Builder.create(this, "WDBackendEcrRepoUri")
                .value(ecrRepo.getRepositoryUri())
                .description("URI of backend ECR repository")
                .build();

        // ECS cluster and Fargate service setup
        Cluster ecsCluster = Cluster.Builder.create(this, "WDEcsCluster")
                .vpc(vpc)
                .build();

        FargateTaskDefinition fargateTaskDefinition = FargateTaskDefinition.Builder.create(this, "WDFargateTaskDefinition")
                .memoryLimitMiB(512)
                .cpu(256)
                .build();

        FargateService fargateService = FargateService.Builder.create(this, "WDFargateService")
                .cluster(ecsCluster)
                .taskDefinition(fargateTaskDefinition)
                .desiredCount(1)
                .build();

        // Output the ECS cluster name and service name
        CfnOutput.Builder.create(this, "WDEcsClusterName")
                .value(ecsCluster.getClusterName())
                .description("WD ECS cluster")
                .build();

        CfnOutput.Builder.create(this, "WDEcsServiceName")
                .value(fargateService.getServiceName())
                .description("WD ECS service")
                .build();
    }
}
