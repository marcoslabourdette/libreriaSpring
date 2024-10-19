package com.libreria.spring.controllers;

import com.libreria.spring.servicios.DataLoadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    public DataLoadService dataService;
    @GetMapping("/")
    public String index(HttpServletRequest request, ModelMap modelo){
        dataService.cargarDatos(request,modelo);
        return "index";
    }
}
