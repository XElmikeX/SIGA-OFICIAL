package com.uns.siiga2.__web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate; // ¡IMPORTANTE!
import org.springframework.stereotype.Component;

/**
 * Esta clase se ejecuta automáticamente al iniciar la aplicación.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate; // Inyecta JdbcTemplate

    @Override
    public void run(String... args) throws Exception {
        
        System.out.println("=== DIAGNÓSTICO DE CONEXIÓN A BD ===");
        
        // Muestra todas las variables de entorno relevantes
        System.out.println("PGHOST: " + System.getenv("PGHOST"));
        System.out.println("PGPORT: " + System.getenv("PGPORT"));
        System.out.println("PGDATABASE: " + System.getenv("PGDATABASE"));
        System.out.println("PGUSER: " + System.getenv("PGUSER"));
        System.out.println("PORT: " + System.getenv("PORT"));
        
        System.out.println("=== FIN DIAGNÓSTICO ===");
        
        try {
            // Intenta conectarse a la BD
            String dbVersion = jdbcTemplate.queryForObject("SELECT version()", String.class);
            System.out.println("✅ Conexión a PostgreSQL exitosa: " + dbVersion);
            
            // Verifica si hay usuarios
            long count = usuarioRepository.count();
            System.out.println("📊 Usuarios en BD: " + count);
            
        } catch (Exception e) {
            System.err.println("❌ ERROR de conexión a BD: " + e.getMessage());
            System.err.println("Posibles causas:");
            System.err.println("1. Variables de entorno no configuradas en Railway");
            System.err.println("2. Servicio PostgreSQL no añadido al proyecto");
            System.err.println("3. Credenciales incorrectas");
            throw e; // Lanza la excepción para que falle temprano
        }
        
        try {
            if (usuarioRepository.count() == 0) {
                System.out.println("No hay usuarios, creando datos iniciales...");

                // --- Usuario Administrador ---
                Usuario admin = new Usuario();
                admin.setUsername("XElmikeX");
                admin.setPassword("71155796");
                admin.setRol(Rol.ADMINISTRADOR);
                usuarioRepository.save(admin);
                
                System.out.println("¡Usuarios iniciales creados con éxito!");
            } else {
                System.out.println("La base de datos ya tiene usuarios. No se crearon datos iniciales.");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al inicializar datos: " + e.getMessage());
        }
    }
}