package com.libreria.spring.repositorios;

import com.libreria.spring.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor,String> {
    @Query("SELECT a FROM Autor a WHERE a.usuarioId = :usuarioId")
    List<Autor> findAutoresByUsuarioId(@Param("usuarioId") String usuarioId);
}
