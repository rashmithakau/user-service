# Step 1: Use a base image with JDK
FROM openjdk:17-jdk-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Spring Boot JAR file into the container
# Replace "user-service.jar" with your actual JAR file name if necessary
COPY target/user-service.jar app.jar

# Step 4: Expose the application port
EXPOSE 8089

# Step 5: Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
