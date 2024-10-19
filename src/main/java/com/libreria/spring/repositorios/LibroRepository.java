package com.libreria.spring.repositorios;

import com.libreria.spring.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro,String> {
    @Query("SELECT l FROM Libro l WHERE l.usuarioId = :usuarioId")
    List<Libro> findLibrosByUsuarioId(@Param("usuarioId") String usuarioId);

}
