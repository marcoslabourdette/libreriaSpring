package com.libreria.spring.servicios;

import com.libreria.spring.entidades.Autor;
import com.libreria.spring.entidades.Editorial;
import com.libreria.spring.entidades.Libro;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private UsuarioService usuarioService;

    public void cargarDatos(HttpServletRequest request, ModelMap modelo) {
        String usuarioId = usuarioService.obtenerUsuarioId(request);
        List<Autor> autores = autorService.listarAutores(usuarioId);
        List<Editorial> editoriales = editorialService.listarEditoriales(usuarioId);
        List<Libro> libros = libroService.listarLibros(usuarioId);
        Collections.sort(autores,Autor.compararNombre);
        Collections.sort(editoriales,Editorial.compararNombre);
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        modelo.addAttribute("libros", libros);
    }
}
