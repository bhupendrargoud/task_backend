# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY /target/payroll-0.0.1-SNAPSHOT.jar /app/payroll.jar

# Expose port 8080 (adjust if your Spring Boot app runs on a different port)
EXPOSE 8080

# Environment variables for MySQL connection
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-container:3306/payroll
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=admin1234
ENV SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver
ENV SPRING_JPA_HIBERNATE_DDL-AUTO=update

# Command to run the application
CMD ["java", "-jar", "payroll.jar"]
