package backofficeapi.domain.exception;

import org.springframework.http.HttpStatus;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRegionalNotFoundException
 *   Descripción: Excepción de negocio cuando no se encuentra una regional
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

public class TblRegionalNotFoundException extends BusinessApiException {

    public TblRegionalNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "No se encontró la regional con ID: " + id);
    }

    public TblRegionalNotFoundException(String code) {
        super(HttpStatus.NOT_FOUND, "No se encontró la regional con código: " + code);
    }
}
