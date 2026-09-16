package backofficeapi.domain.exception;

import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthUserNotFoundException
 *   Descripción: Excepción de negocio cuando no se encuentra un usuario
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Soporte para búsqueda por ID y username
 *----------------------------------------
 */

public class AuthUserNotFoundException extends BusinessApiException {

    public AuthUserNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + id);
    }

    public AuthUserNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, "Usuario no encontrado con el nombre de usuario: " + username);
    }
}
