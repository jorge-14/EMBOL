package backofficeapi.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
<<<<<<< HEAD
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ExternalApiException
 *   Descripción: Excepción para errores de servicios externos
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */
=======
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   07.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */


>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
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
