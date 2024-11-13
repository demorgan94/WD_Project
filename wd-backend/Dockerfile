# Use a Java runtime image
FROM openjdk:17-jdk-alpine
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]