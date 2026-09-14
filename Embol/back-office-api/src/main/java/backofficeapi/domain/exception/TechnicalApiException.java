package backofficeapi.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
<<<<<<< HEAD
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TechnicalApiException
 *   Descripción: Excepción base para errores técnicos
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
<<<<<<< HEAD
        this(httpStatus, errorCode, cause, new Object[] {});
=======
        this(httpStatus, errorCode, cause, new Object[]{});
    }

    private static String resolveMessage(String errorCode, Object... args) {
        return errorCode;
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
    }

    public TechnicalApiException(HttpStatus httpStatus, String errorCode, Object... args) {
        super(resolveMessage(errorCode, args));
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.args = args;
    }

    public TechnicalApiException(HttpStatus httpStatus, String errorCode) {
<<<<<<< HEAD
        this(httpStatus, errorCode, new Object[] {});
    }

    private static String resolveMessage(String errorCode, Object... args) {
        return errorCode;
=======
        this(httpStatus, errorCode, new Object[]{});
>>>>>>> 286c1944e9a0455898fb3d32a06a876229a9e002
    }
}
