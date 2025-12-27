# Dockerfile - VERSIÓN CORREGIDA
# Etapa 1: Build con Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar solo el pom.xml primero para cachear dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar JAR desde etapa de build
COPY --from=build /app/target/siiga2-web-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/health || exit 1

# Comando de inicio - Maneja PORT de Railway
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-8080} -jar app.jar"]