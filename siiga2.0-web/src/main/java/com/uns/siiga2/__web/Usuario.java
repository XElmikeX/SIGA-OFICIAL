package com.uns.siiga2.__web; // Tu paquete

// Importamos las herramientas de Jakarta Persistence (JPA)
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Esta clase @Entity le dice a Spring que debe crear 
 * una tabla llamada "usuario" en la base de datos.
 */
@Entity
public class Usuario {

    // @Id le dice a Spring que este es el ID único (llave primaria).
    @Id
    // @GeneratedValue le dice a la BD que genere este número automáticamente.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    // @Enumerated le dice a Spring cómo guardar el Enum "Rol" que creamos.
    // "STRING" es la mejor opción: guardará el texto "ADMINISTRADOR", etc.
    @Enumerated(EnumType.STRING)
    private Rol rol;

    // --- Getters y Setters ---
    // Métodos para obtener y establecer los valores.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}