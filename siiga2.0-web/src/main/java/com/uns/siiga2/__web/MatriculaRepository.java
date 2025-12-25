package com.uns.siiga2.__web;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    // 1. Para el PROFESOR:
    // Buscar todos los alumnos matriculados en UN curso específico.
    // Esto servirá para mostrar la lista de asistencia y poner notas.
    List<Matricula> findByCursoId(Long cursoId);

    // 2. Para el ALUMNO:
    // Buscar todas las matrículas de UN alumno específico.
    // Esto servirá para que el alumno vea sus notas de todas sus materias.
    List<Matricula> findByAlumnoId(Long alumnoId);
    
    // 3. Para evitar duplicados (Opcional pero útil):
    // Buscar si un alumno YA está en un curso específico.
    Matricula findByCursoIdAndAlumnoId(Long cursoId, Long alumnoId);
}