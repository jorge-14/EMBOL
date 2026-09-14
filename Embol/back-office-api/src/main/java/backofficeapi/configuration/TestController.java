package backofficeapi.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/hello")
    public Map<String, Object> hello(Authentication authentication) {

        Map<String, Object> response = new HashMap<>();

        response.put(
                "message",
                "Hola, te has autenticado correctamente!"
        );

        response.put(
                "username",
                authentication.getName()
        );

        response.put(
                "authorities",
                authentication.getAuthorities()
        );

        return response;
    }
}
