package backofficeapi.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TechnicalApiException
 *   Descripción: Excepción base para errores técnicos
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */
@Getter
public class TechnicalApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final Object[] args;

    public TechnicalApiException(HttpStatus httpStatus, String errorCode, Throwable cause, Object... args) {
        super(resolveMessage(errorCode, args), cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.args = args;
    }

    public TechnicalApiException(HttpStatus httpStatus, String errorCode, Throwable cause) {
        this(httpStatus, errorCode, cause, new Object[] {});
    }

    public TechnicalApiException(HttpStatus httpStatus, String errorCode, Object... args) {
        super(resolveMessage(errorCode, args));
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.args = args;
    }

    public TechnicalApiException(HttpStatus httpStatus, String errorCode) {
        this(httpStatus, errorCode, new Object[] {});
    }

    private static String resolveMessage(String errorCode, Object... args) {
        return errorCode;
    }
}
