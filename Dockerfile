# ============================================================
# Multi-stage Dockerfile
# Stage 1: Build JAR using Maven (Render doesn't need pre-built JAR)
# Stage 2: Run the JAR
# ============================================================

# Stage 1 - Build
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies first (Docker cache layer)
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn clean package -DskipTests -q

# Stage 2 - Run
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
