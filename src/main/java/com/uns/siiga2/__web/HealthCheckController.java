// HealthCheckController.java - Colócalo en tu paquete com.uns.siiga2.__web
package com.uns.siiga2.__web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthCheckController {
    
    @GetMapping("/")
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
}
