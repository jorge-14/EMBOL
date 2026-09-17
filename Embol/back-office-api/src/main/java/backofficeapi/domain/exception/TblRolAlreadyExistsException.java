package backofficeapi.domain.exception;

import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRolAlreadyExistsException
 *   Descripción: Excepción de negocio cuando un rol ya existe
 *   Author Prog: Douglas Javieri Vino
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Douglas Javieri Vino | Creación Inicial
 *----------------------------------------
 */

public class TblRolAlreadyExistsException extends BusinessApiException {

    public TblRolAlreadyExistsException(String name) {
        super(HttpStatus.CONFLICT, "Ya existe un rol con el nombre: " + name);
    }
}
