package backofficeapi.domain.exception;

import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthUserAlreadyExistsException
 *   Descripción: Excepción de negocio cuando un usuario ya existe
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Actualización de mensaje
 *----------------------------------------
 */

public class AuthUserAlreadyExistsException extends BusinessApiException {

    public AuthUserAlreadyExistsException(String identifier) {
        super(HttpStatus.CONFLICT, "Ya existe un usuario con el identificador: " + identifier);
    }
}
