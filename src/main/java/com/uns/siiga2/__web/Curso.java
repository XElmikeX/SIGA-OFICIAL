package com.uns.siiga2.__web; // Tu paquete

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Esta es la "plantilla" o "molde" para la tabla "curso"
 * en la base de datos.
 */
@Entity // Le dice a Spring que esto es una tabla de BD
public class Curso {

    @Id // Marca este campo como la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La BD le dará un ID automático
    private Long id;
    
    private String nombreCurso; // Ej: "Matemática Aplicada"
    private String horario;       // Ej: "Lunes 8-10, Miércoles 9-11"
    
    // Esta es la forma simple de "asignar" un curso.
    // Simplemente guardaremos el 'username' del docente.
    private String docenteUsername; // Ej: "nuevo_profe"

    // --- Getters y Setters ---
    // (Puedes generarlos automáticamente en NetBeans:
    // Clic derecho > Insert Code... > Getter and Setter... > Marcar todos > Generate)
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDocenteUsername() {
        return docenteUsername;
    }

    public void setDocenteUsername(String docenteUsername) {
        this.docenteUsername = docenteUsername;
    }
}