package com.libreria.spring.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Libro {
    @Id
    @Column(nullable=false)
    private String isbn;
    @Column(nullable=false)
    private String titulo;
    @Column(nullable=false)
    private String descripcion;
    @Column(nullable=false)
    private String imagen_url;
    @Temporal(TemporalType.DATE)
    private Date alta;
    @ManyToOne
    private Autor autor;
    @ManyToOne
    private Editorial editorial;

    public static Comparator<Libro> compararPorTitulo = new Comparator<Libro>() {
        @Override
        public int compare(Libro libro1, Libro libro2) {
            return libro1.getTitulo().compareTo(libro2.getTitulo());
        }
    };
}