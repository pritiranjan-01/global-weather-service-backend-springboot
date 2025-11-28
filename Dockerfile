# Use official Java 17 JDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy JAR file from Maven target folder
COPY target/*.jar app.jar

# Expose the port that Render will assign
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
