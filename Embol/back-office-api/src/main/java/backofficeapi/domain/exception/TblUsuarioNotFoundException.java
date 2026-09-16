package backofficeapi.domain.exception;

import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUsuarioNotFoundException
 *   Descripción: Excepción de negocio cuando no se encuentra un usuario
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Nomenclatura TblUsuarioNotFoundException
 *----------------------------------------
 */

public class TblUsuarioNotFoundException extends BusinessApiException {

    public TblUsuarioNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: " + id);
    }

    public TblUsuarioNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, "Usuario no encontrado con el nombre de usuario: " + username);
    }
}
