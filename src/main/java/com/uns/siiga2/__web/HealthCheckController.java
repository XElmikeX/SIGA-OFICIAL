package com.uns.siiga2.__web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheckController {
    
    // Cambia de "/" a "/health" o "/info"
    @GetMapping("/info")
    public String home() {
        return "SIIGA 2.0 - Sistema de Gestión Académica - Universidad Nacional del Santa";
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "SIIGA 2.0");
        response.put("version", "0.0.1");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/actuator/health")
    public ResponseEntity<Map<String, String>> actuatorHealth() {
        return health();
    }
    
    // También puedes añadir un endpoint de debug
    @GetMapping("/debug")
    public ResponseEntity<Map<String, String>> debug() {
        Map<String, String> response = new HashMap<>();
        response.put("PGHOST", System.getenv("PGHOST"));
        response.put("PGPORT", System.getenv("PGPORT"));
        response.put("PGDATABASE", System.getenv("PGDATABASE"));
        response.put("PGUSER", System.getenv("PGUSER"));
        response.put("PGPASSWORD", System.getenv("PGPASSWORD") != null ? "***SET***" : "NULL");
        response.put("PORT", System.getenv("PORT"));
        response.put("java_version", System.getProperty("java.version"));
        return ResponseEntity.ok(response);
    }
}
