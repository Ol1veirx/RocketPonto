# Imagem base para o OpenJDK 17
FROM openjdk:17-jdk-slim

# Diretório de trabalho no container
WORKDIR /app

# Copie o JAR gerado para dentro do container
COPY target/RocketPonto-0.0.1-SNAPSHOT.jar app.jar

# Defina o comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
