package com.libreria.spring.repositorios;

import com.libreria.spring.entidades.Autor;
import com.libreria.spring.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial,String> {
    @Query("SELECT e FROM Editorial e WHERE e.usuarioId = :usuarioId")
    List<Editorial> findEditorialesByUsuarioId(@Param("usuarioId") String usuarioId);
}
