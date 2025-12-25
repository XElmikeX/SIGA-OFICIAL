FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el proyecto
COPY . .

# Dar permisos al mvnw
RUN chmod +x mvnw

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Puerto expuesto
EXPOSE 8095

# Comando de ejecución
CMD ["java", "-jar", "target/siiga2.0-web-0.0.1-SNAPSHOT.jar"]