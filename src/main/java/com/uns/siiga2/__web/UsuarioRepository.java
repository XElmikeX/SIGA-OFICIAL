package com.uns.siiga2.__web;

import java.util.List; 
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);
    
    // --- NUEVO MÉTODO ---
    // Busca todos los usuarios que tengan un Rol específico (ej. ALUMNO)
    List<Usuario> findByRol(Rol rol);
}