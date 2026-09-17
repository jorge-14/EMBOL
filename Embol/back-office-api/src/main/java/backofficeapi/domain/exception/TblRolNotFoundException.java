package backofficeapi.domain.exception;

import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRolNotFoundException
 *   Descripción: Excepción de negocio cuando no se encuentra un rol
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

public class TblRolNotFoundException extends BusinessApiException {

    public TblRolNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Rol no encontrado con ID: " + id);
    }

    public TblRolNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, "Rol no encontrado con el nombre: " + name);
    }
}
