LABEL authors="mohamed"
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/myapp.jar app.jar
CMD ["java", "-jar", "app.jar"]


