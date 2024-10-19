package com.libreria.spring.servicios;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    public String obtenerUsuarioId(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String usuarioId = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("usuarioId".equals(cookie.getName())) {
                    usuarioId = cookie.getValue();
                    break;
                }
            }
        }
        return usuarioId;
    }
}
