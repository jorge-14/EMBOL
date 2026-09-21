package backofficeapi.application.port.input.resource;

import backofficeapi.infrastructure.adapter.input.rest.request.tblResource.SaveResourceAccessRequestDto;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: SaveResourceAccessUseCase
 *   Descripción: Puerto de entrada para guardar la asignación de accesos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial en módulo resource
 *----------------------------------------
 */
public interface SaveResourceAccessUseCase {
    void saveAccess(SaveResourceAccessRequestDto request);
}
