package com.libreria.spring.controllers;

import com.libreria.spring.excepciones.MyException;
import com.libreria.spring.reportes.LibroExporterPDF;
import com.libreria.spring.servicios.DataLoadService;
import com.libreria.spring.servicios.LibroService;
import com.libreria.spring.servicios.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService libroService;
    @Autowired
    private DataLoadService dataService;
    @Autowired
    private LibroExporterPDF LibroPdfExporter;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar")
    public String registro(HttpServletRequest request,@RequestParam(required = false) String isbn, @RequestParam String titulo, @RequestParam String descripcion, @RequestParam String imagen, @RequestParam String autorID, @RequestParam String editorialID, RedirectAttributes redirectAttributes) {
        try {
            String usuarioId = usuarioService.obtenerUsuarioId(request);
            libroService.crearLibro(usuarioId,isbn.trim(), titulo.trim(), descripcion.trim(), imagen.trim(), autorID, editorialID);
            redirectAttributes.addFlashAttribute("exito", "¡El libro se agregó correctamente! 😎");
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage() + " 😬");
        }
        return "redirect:/#libros";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(HttpServletRequest request,@PathVariable String isbn, @RequestParam String titulo, @RequestParam String descripcion, @RequestParam String imagen, @RequestParam String autorID, @RequestParam String editorialID, ModelMap modelo, RedirectAttributes redirectAttributes) throws MyException {
        try {
            libroService.modificarLibro(isbn.trim(), titulo.trim(), descripcion.trim(), imagen.trim(), autorID, editorialID);
            redirectAttributes.addFlashAttribute("exito", "¡El libro se modificó correctamente! 😎");
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        dataService.cargarDatos(request,modelo);
        return "redirect:/#libros";
    }

    @GetMapping("/eliminar/{isbn}")
    public String eliminarLibro(@PathVariable String isbn, RedirectAttributes redirectAttributes) {
        try {
            libroService.eliminarLibro(isbn);
            redirectAttributes.addFlashAttribute("exito", "¡El libro se eliminó correctamente! 👋🏼");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/#libros";
    }

    @GetMapping("/reportes")
    public ResponseEntity<byte[]> descargarReporte(HttpServletRequest request,RedirectAttributes redirectAttributes) {
        // Nombre temporal del archivo PDF
        String nombreArchivo = "Libros[LibrarySB].pdf";

        try {
            String usuarioId = usuarioService.obtenerUsuarioId(request);
            LibroPdfExporter.exportar(libroService.listarLibros(usuarioId), nombreArchivo);
            FileInputStream fis = new FileInputStream(nombreArchivo);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            FileCopyUtils.copy(fis, bos);
            byte[] contenidoPdf = bos.toByteArray();
            fis.close();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo);
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
            redirectAttributes.addFlashAttribute("exito", "Reporte descargado exitosamente ");
            return new ResponseEntity<>(contenidoPdf, headers, HttpStatus.OK);
        } catch (MyException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage() + " 😬");
            return ResponseEntity.ok().body(null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Archivo no encontrado: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error de lectura/escritura: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
