package com.uns.siiga2.__web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        logger.info("🚀 Iniciando SIIGA 2.0 - Sistema de Gestión Académica");
        logger.info("📊 Versión: 0.0.1-SNAPSHOT");
        logger.info("🎯 Universidad Nacional del Santa");
        
        try {
            SpringApplication.run(Application.class, args);
            logger.info("✅ Aplicación iniciada correctamente en puerto: " + 
                (System.getenv("PORT") != null ? System.getenv("PORT") : "8080"));
        } catch (Exception e) {
            logger.error("❌ ERROR al iniciar la aplicación: " + e.getMessage(), e);
            throw e;
        }
    }
}