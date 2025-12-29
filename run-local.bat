# run-local.bat (para Windows)
@echo off

echo Deteniendo contenedores existentes...
docker-compose down

echo Iniciando PostgreSQL...
docker-compose up -d postgres

echo Esperando a que PostgreSQL inicie...
timeout /t 5 /nobreak > nul

echo Compilando aplicacion...
mvn clean compile

echo Iniciando aplicacion Spring Boot...
set ACTIVE_PROFILE=local
mvn spring-boot:run