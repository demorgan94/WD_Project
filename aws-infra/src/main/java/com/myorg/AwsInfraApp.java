package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Aws;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

public class AwsInfraApp {
    public static void main(final String[] args) {
        App app = new App();

        new AwsInfraStack(app, "AwsInfraStack", StackProps.builder()
                .env(Environment.builder()
                        .account(Aws.ACCOUNT_ID)
                        .region(Aws.REGION)
                        .build())
                .build());

        app.synth();
    }
}

