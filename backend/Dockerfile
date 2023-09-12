# Use the official maven/Java 8 image to create a build artifact.
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory.
WORKDIR /usr/src/app

# Copy the pom.xml file and download all the dependencies. 
# This layer is recreated only when pom.xml changes thus 
# dependencies are cached for subsequent builds.
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the project source and build the application.
COPY src ./src
RUN mvn clean package -DskipTests

# Use OpenJDK to run the app.
FROM openjdk:17.0.1-jdk-slim

# Set the working directory.
WORKDIR /app

# Copy the jar file from the build stage.
COPY --from=build /usr/src/app/target/*.jar app.jar

# Expose the port the app runs on.
EXPOSE 8081

# Command to run the application.
CMD ["java", "-jar", "app.jar"]
