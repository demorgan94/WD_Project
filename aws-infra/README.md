# WD CDK Java Project

This is an AWS [CDK](https://github.com/aws/aws-cdk) Java project.

The `cdk.json` file tells the CDK Toolkit how to execute the app.

## What is AWS CDK?

AWS CDK is a tool for defining and provisioning infrastructure as code (IaC) on AWS using 
[CloudFormation](https://aws.amazon.com/cloudformation/) templates but in a more developer-friendly way.

As we know (or if you don't), CloudFormation templates are written in JSON or YAML, and you need to have a deep 
understanding of AWS services to actually write templates and understand the underlying concepts.

With CDK, you are actually writing CloudFormation templates, but in a more programmatic way, CDK supports different 
programming languages like Javascript, TypeScript, Java, Python, etc., for this purpose.

CDK simplifies the process of defining and deploying AWS infrastructure using CloudFormation templates, introducing a 
higher level of abstraction, hiding the underlying complexity of CloudFormation and exposing a simpler API, at the end 
you will end up with a CloudFormation template but with the power of a programming language.

### How to use
First, make sure you have the CDK CLI installed and configured.

Make sure you have Node.js installed (Yeah, the first language supported by CDK was TypeScript but don't worry, 
that means it comes as an easy to install NPM package).

- https://nodejs.org/en/download/package-manager/

If you already have Node.js installed, run `npm install -g aws-cdk`

*For references check CDK docs: https://docs.aws.amazon.com/cdk/latest/guide/cli.html*

The CDK will pull your AWS credentials from your default AWS profile inside ~/.aws/credentials, if no AWS CLI configured
check https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-quickstart.html.

## Useful commands

 * `mvn package`     compile and run tests
 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk bootstrap`   prepares an environment for deployment
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk destroy`     delete this stack
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation

Enjoy!
