### Stage 1: Build the application using Maven inside Docker
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy pom.xml and download dependencies (better caching)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy the rest of your project
COPY src ./src

# Build the jar
RUN mvn -B package -DskipTests


### Stage 2: Run the Spring Boot jar
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
