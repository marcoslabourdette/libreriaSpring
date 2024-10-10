package com.libreria.spring.servicios;

import com.libreria.spring.entidades.Autor;
import com.libreria.spring.entidades.Editorial;
import com.libreria.spring.entidades.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Collections;
import java.util.List;

@Service
public class DataLoadService {

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private LibroService libroService;

    public void cargarDatos(ModelMap modelo) {
        List<Autor> autores = autorService.listarAutores();
        List<Editorial> editoriales = editorialService.listarEditoriales();
        List<Libro> libros = libroService.listarLibros();
        Collections.sort(autores,Autor.compararNombre);
        Collections.sort(editoriales,Editorial.compararNombre);
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        modelo.addAttribute("libros", libros);
    }
}
