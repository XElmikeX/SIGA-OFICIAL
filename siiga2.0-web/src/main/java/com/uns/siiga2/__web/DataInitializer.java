package com.uns.siiga2.__web; // Tu paquete

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Esta clase se ejecuta automáticamente al iniciar la aplicación.
 * Implementa CommandLineRunner, lo que significa que su método "run"
 * será llamado después de que Spring haya cargado todo.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    // 1. Inyectamos nuestro repositorio para poder guardar cosas en la BD.
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        
        // 2. Revisamos si ya hay usuarios en la BD.
        if (usuarioRepository.count() == 0) {
            // Si no hay usuarios, creamos los de ejemplo:

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
    }
}