# Usa Java 17 (compatible con Railway)
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Imagen final más ligera
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# PARA DEBUG: Verifica que el JAR existe
RUN ls -la /app/

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar

EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "app.jar"]