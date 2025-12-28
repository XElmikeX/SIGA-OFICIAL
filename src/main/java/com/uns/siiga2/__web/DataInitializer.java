package com.uns.siiga2.__web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Esta clase se ejecuta automáticamente al iniciar la aplicación.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
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