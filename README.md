# WD Project
This project was built as part of the Western Digital interview process.

## Description
This project is a simple backend CRUD application built using Spring Boot. 
It allows users to create, read, update, and delete products using a Swagger UI.
The application uses a MySQL database to store the product data.

The architecture used for this project is the one suggested by Uncle Bob on his popular book
[Clean Architecture](https://www.oreilly.com/library/view/clean-architecture-a/9780134494294/).
Uncle Bob is a famous software craftsman who has written many books on software architecture.

### Clean Architecture
Clean architecture is a series of principals and design patterns which main focus is the 
separation of concerns. Following this focus we can have a more maintainable and testable codebase.

I applied these principals on this project, although it is not a big project, it's a good example.

Clean architecture suggests to separate the codebase into different layers which interact with each other
following the *Dependency Rule*, which stands that `Source code dependencies can only point inwards`.
Let's check the following diagram:

![img.png](img.png)

*Depending on your application, you can have more or less layers and different names, but the 
Dependency Rule applies.*

The diagram above represents the separation of the layers and how they interact between them,
each layer is responsible for a specific task.

Now, this is how I structured this project to implement clean architecture:
- **Domain**: This layer is responsible for the enterprise business logic, here I defined the models, 
exceptions and gateways interfaces that will be used by the outer layer, this layer encapsulates the 
most general and high-level rules, and are the least likely to change.
- **Application**: This layer is responsible for the application business logic, here I encapsulated and
implemented the use cases of the application. These use cases orchestrate the flow of data to and from the
domain layer, I added here the adapters which are the gateways to the outer layers, we don't expect
this layer to be affected by any changes to externalities such as the database, the UI, or any other
common framework.
- **Presentation**: This layer is responsible for the presentation of the application, here I implemented
the controllers, which are the entry points of the application, the DTOs for the requests and responses,
and some validations and the gateways that interact with the outer layer to receive data from the DB.
- **Infrastructure**: This layer is responsible for the infrastructure of the application, here I 
implemented the configuration for everything external to our system, such as the database connection
and entities.
***
## Usage
*Before running the application, make sure you have the following AWS tools installed
and configured:*
- [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html)
- [AWS CDK](https://docs.aws.amazon.com/cdk/latest/guide/getting-started.html)

*This project was built using the following Java Version:*
- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
***
To run the application, execute the following commands:
#### Aws CDK
```
cdk bootstrap
cdk deploy
```
#### SpringBoot
```
./gradlew clean build
./gradlew bootRun
```
