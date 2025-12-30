package com.uns.siiga2.__web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) { 
        try {
            System.out.println("🚀 SIIGA 2.0 - INICIANDO...");
            
            // SIEMPRE usa el mismo application.properties
            // NO hay perfiles locales
            SpringApplication.run(Application.class, args);
            
            System.out.println("✅ APLICACIÓN INICIADA");
            System.out.println("📊 Conectado a: PostgreSQL Railway");
            System.out.println("🌐 Puerto: 8080");
            
        } catch (Exception e) {
            System.err.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}


