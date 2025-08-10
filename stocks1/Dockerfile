# Use an official OpenJDK image as the base
FROM openjdk:17-jdk-slim

# Set environment variables for the database connection
ENV DB_HOST=db
ENV DB_PORT=3306
ENV DB_NAME=stockPortfolioDB
ENV DB_USER=root
ENV DB_PASSWORD=Ani1Yotam!

# Copy the JAR file into the container
COPY target/stocks1-1.0-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8000

# Run the Spring Boot application on port 8000
ENTRYPOINT java -Dserver.port=8000 \
                -Dspring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME} \
                -Dspring.datasource.username=${DB_USER} \
                -Dspring.datasource.password=${DB_PASSWORD} \
                -jar /app.jar

