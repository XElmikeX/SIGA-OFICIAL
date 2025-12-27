package com.uns.siiga2.__web; 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Esta es la "plantilla" o "molde" para la tabla "matricula".
 * Esta tabla es el "puente" que conecta a un Alumno con un Curso
 * y guarda sus notas.
 */
@Entity // Le dice a Spring que esto es una tabla de BD
public class Matricula {

    @Id // Marca este campo como la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // La BD le dará un ID automático
    private Long id;

    // --- Relaciones (Las conexiones) ---

    // @ManyToOne: "Muchas matrículas pueden pertenecer a UN Alumno"
    // Esto crea una columna en la BD llamada 'alumno_id'
    @ManyToOne 
    @JoinColumn(name = "alumno_id")
    private Usuario alumno;

    // @ManyToOne: "Muchas matrículas pueden pertenecer a UN Curso"
    // Esto crea una columna en la BD llamada 'curso_id'
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    // --- Campos para las Notas ---
    
    // Usamos Double para poder guardar notas con decimales (ej. 15.5)
    private Double nota1;
    private Double nota2;
    private Double promedio;
    
    // --- Getters y Setters ---
    // (Puedes generarlos automáticamente en NetBeans:
    // Clic derecho > Insert Code... > Getter and Setter... > Marcar todos > Generate)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getAlumno() {
        return alumno;
    }

    public void setAlumno(Usuario alumno) {
        this.alumno = alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Double getNota1() {
        return nota1;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
    }

    public Double getNota2() {
        return nota2;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }
}