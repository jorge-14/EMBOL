package backofficeapi.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción: Excepción base para errores de negocio
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   07.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
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
