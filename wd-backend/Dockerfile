# Use Amazon Corretto 21 as the base image
FROM amazoncorretto:21

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the build/libs folder
COPY build/libs/*.jar app.jar

# Expose the port your app will run on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]