package backofficeapi.domain.exception;

import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthRegionalNotFoundException
 *   Descripción: Excepción de negocio lanzada cuando no se encuentra una regional
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
public class AuthRegionalNotFoundException extends BusinessApiException {

    public AuthRegionalNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "error.authRegional.notFound", id);
    }

    public AuthRegionalNotFoundException(String code) {
        super(HttpStatus.NOT_FOUND, "error.authRegional.codeNotFound", code);
    }
}
