package backofficeapi.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   07.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */


@Getter
public class ExternalApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String response;

    public ExternalApiException(HttpStatus httpStatus, String errorCode, String response) {
        super(errorCode);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.response = response;
    }
}
