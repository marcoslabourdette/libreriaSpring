package com.libreria.spring.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.Comparator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable=false)
    private String nacionalidad;
    @Column(nullable=false)
    private LocalDate nacimiento;
    @Column(nullable=false)
    private String bio;
    @Column(nullable=false)
    private String imagenUrl;
    @Column(nullable = false)
    private boolean alta;

    ///Metodo para comparar nombre y ordenarlos
    public static Comparator<Autor> compararNombre = new Comparator<Autor>(){
        @Override
        public int compare(Autor a1, Autor a2){
            return a1.getNombre().compareTo(a2.getNombre());
        }
    };

}