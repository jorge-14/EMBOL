package backofficeapi.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
<<<<<<< HEAD
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: BusinessApiException
 *   Descripción: Excepción base para errores de negocio
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
=======
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   07.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
 *----------------------------------------
 */
@Getter
public class BusinessApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final Object[] args;

    public BusinessApiException(HttpStatus httpStatus, String errorCode, Object... args) {
        super(resolveMessage(errorCode, args));
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.args = args;
    }

    public BusinessApiException(HttpStatus httpStatus, String errorCode) {
        this(httpStatus, errorCode, new Object[] {});
    }

    private static String resolveMessage(String errorCode, Object... args) {
        return errorCode;
    }
}
