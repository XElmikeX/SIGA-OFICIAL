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