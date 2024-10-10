package com.libreria.spring.controllers;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.libreria.spring.excepciones.MyException;
import com.libreria.spring.reportes.AutorExporterPDF;
import com.libreria.spring.servicios.AutorService;
import com.libreria.spring.servicios.DataLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorService as;
    @Autowired
    private DataLoadService dataService;
    @Autowired
    private AutorExporterPDF AutorPdfExporter;

    @PostMapping("/registrar")
    public String registrarAutor(@RequestParam String nombre, @RequestParam String nacionalidad, @RequestParam String nacimiento, @RequestParam String bio, @RequestParam String imagenUrl, RedirectAttributes redirectAttributes) {
        try {
            as.crearAutor(nombre.trim(), nacionalidad.trim(), nacimiento.trim(), bio.trim(), imagenUrl.trim());
            redirectAttributes.addFlashAttribute("exito", "¡El autor se registró correctamente! 😎");
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage() + " 😬");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/#autores";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, @RequestParam String nombre, @RequestParam String nacionalidad, @RequestParam String nacimiento, @RequestParam String imagenUrl, @RequestParam String bio, ModelMap modelo, RedirectAttributes redirectAttributes) throws MyException {
        try {
            as.modificarAutor(id, nombre.trim(), nacionalidad.trim(), nacimiento.trim(), imagenUrl.trim(), bio.trim());
            redirectAttributes.addFlashAttribute("exito", "¡El autor se modificó correctamente! 😎");
        } catch (MyException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage() + " 😬");
        }
        dataService.cargarDatos(modelo);
        return "redirect:/#autores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAutor(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            as.eliminarAutor(id);
            redirectAttributes.addFlashAttribute("exito", "¡Se eliminó el autor correctamente! 👋🏼");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "El autor está registrado en un libro 😬");
        }
        return "redirect:/#autores";
    }

    @GetMapping("/reportes")
    public ResponseEntity<byte[]> descargarReporte(RedirectAttributes redirectAttributes) {
        String nombreArchivo = "Autores[LibrarySB].pdf";

        try {
            AutorPdfExporter.exportar(as.listarAutores(), nombreArchivo);
            FileInputStream fis = new FileInputStream(nombreArchivo);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            FileCopyUtils.copy(fis, bos);
            byte[] contenidoPdf = bos.toByteArray();
            fis.close();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivo);
            headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
            redirectAttributes.addFlashAttribute("exito", "Reporte descargado exitosamente");
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
