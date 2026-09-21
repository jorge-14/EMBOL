package backofficeapi.application.port.input.access;

import backofficeapi.infrastructure.adapter.input.rest.request.tblAccess.SaveResourceAccessRequestDto;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: SaveResourceAccessUseCase
 *   Descripción: Puerto de entrada para guardar asignación de accesos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
public interface SaveResourceAccessUseCase {
    void saveAccess(SaveResourceAccessRequestDto request);
}
