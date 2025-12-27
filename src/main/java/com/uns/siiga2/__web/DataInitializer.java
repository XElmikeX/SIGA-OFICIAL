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
        
        System.out.println("=== INICIANDO VERIFICACIÓN DE BD ===");
        
        try {
            // Prueba de conexión a la BD
            String dbVersion = jdbcTemplate.queryForObject("SELECT version()", String.class);
            System.out.println("✅ Conexión a PostgreSQL exitosa: " + dbVersion);
        } catch (Exception e) {
            System.err.println("❌ ERROR de conexión a BD: " + e.getMessage());
            // No lanzamos la excepción, solo registramos el error
            // throw e; // Comentado para permitir que la app inicie incluso si hay error
        }
        
        // Verificar si ya hay usuarios en la BD
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