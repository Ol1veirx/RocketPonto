# Etapa 1: Build da aplicação
FROM maven:latest AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Construção da imagem final
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/RocketPonto-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties /app/config/application.properties
ENTRYPOINT ["java", "-jar", "app.jar"]
