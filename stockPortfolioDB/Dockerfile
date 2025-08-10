# Use an official MySQL image from the Docker Hub
FROM mysql:8.0

# Set environment variables for MySQL
ENV MYSQL_ROOT_PASSWORD=Ani1Yotam!
ENV MYSQL_DATABASE=stockPortfolioDB
ENV MYSQL_USER=stockuser
ENV MYSQL_PASSWORD=Ani1Yotam!

# Copy SQL scripts to initialize the database and tables
COPY ./init.sql /docker-entrypoint-initdb.d/

# Expose MySQL port
EXPOSE 3306

