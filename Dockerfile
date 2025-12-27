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
EXPOSE 8095
ENTRYPOINT ["java", "-jar", "-Dserver.port=${PORT:-8095}", "app.jar"]