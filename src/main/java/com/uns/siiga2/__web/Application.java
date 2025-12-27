package com.uns.siiga2.__web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("🚀 INICIANDO SIIGA 2.0 - UNS");
        System.out.println("📊 Versión: 0.0.1-SNAPSHOT");
        System.out.println("🔧 Puerto: " + (System.getenv("PORT") != null ? System.getenv("PORT") : "8080"));
        System.out.println("💾 Java: " + System.getProperty("java.version"));
        
        // Debug: Muestra todas las variables de entorno relevantes
        System.out.println("=== VARIABLES DE ENTORNO ===");
        System.out.println("PGHOST: " + System.getenv("PGHOST"));
        System.out.println("PGPORT: " + System.getenv("PGPORT"));
        System.out.println("PGDATABASE: " + System.getenv("PGDATABASE"));
        System.out.println("PGUSER: " + System.getenv("PGUSER"));
        System.out.println("PGPASSWORD: " + (System.getenv("PGPASSWORD") != null ? "***SET***" : "NULL"));
        
        System.out.println("==========================================");
        
        try {
            SpringApplication.run(Application.class, args);
            System.out.println("✅ APLICACIÓN INICIADA CORRECTAMENTE");
        } catch (Exception e) {
            System.err.println("❌ ERROR CRÍTICO: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}