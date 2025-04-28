# Use an official OpenJDK runtime as a parent image
FROM maven:3-eclipse-temurin-17 AS build

# Set the working directory
WORKDIR /app

# Copy only necessary files
COPY pom.xml ./
COPY src ./src

# Build the application
RUN mvn package -DskipTests

FROM eclipse-temurin:17-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]