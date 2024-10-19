package com.libreria.spring.controllers;

import com.libreria.spring.servicios.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @GetMapping("/setUsuarioId")
    public ResponseEntity<String> setUsuarioId(HttpServletRequest request, HttpServletResponse response) {
        String usuarioId = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("usuarioId".equals(cookie.getName())) {
                    usuarioId = cookie.getValue();
                    break;
                }
            }
        }
        if (usuarioId == null) {
            usuarioId = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("usuarioId", usuarioId);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 30);
            response.addCookie(cookie);
            return ResponseEntity.ok("Nuevo usuario ID set: " + usuarioId);
        }
        return ResponseEntity.ok("usuario ID ya existe: " + usuarioId);
    }
}
