package com.uns.siiga2.__web; // Tu paquete

import com.uns.siiga2.__web.Curso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Este es el Repositorio de Cursos. Extiende de JpaRepository,
 * lo que nos da todos los métodos CRUD para la entidad "Curso".
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Spring Data JPA es lo suficientemente inteligente como para crear
    // una consulta SQL basada en el nombre de este método.
    //
    // "findByDocenteUsername" se traduce automáticamente a:
    // "SELECT * FROM curso WHERE docente_username = ?"
    //
    // Esto nos permitirá encontrar todos los cursos de un docente específico.
    List<Curso> findByDocenteUsername(String username);
    
}