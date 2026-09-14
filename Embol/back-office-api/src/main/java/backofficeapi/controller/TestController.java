package backofficeapi.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @GetMapping("/hello")
    public Map<String, Object> hello(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hola, te has autenticado correctamente!");
        response.put("username", authentication.getName());
        response.put("authorities", authentication.getAuthorities());
        return response;
    }
}
