# Etapa 1: Build con Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Runtime ligero
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Debug: verificar que el JAR existe
RUN ls -la /app/

EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT}", "app.jar"]