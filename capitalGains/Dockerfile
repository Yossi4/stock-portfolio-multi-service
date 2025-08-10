# Use a base image with Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file into the container
# Update the file name to match your target directory
COPY target/capitalGains-1.0-SNAPSHOT.jar capital-gains.jar

# Expose the port your application runs on
EXPOSE 8080

# Define the command to run the application
CMD ["java", "-jar", "capital-gains.jar"]

