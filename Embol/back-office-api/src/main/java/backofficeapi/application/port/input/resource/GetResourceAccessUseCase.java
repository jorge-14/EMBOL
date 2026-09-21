package backofficeapi.application.port.input.resource;

import backofficeapi.infrastructure.adapter.input.rest.response.tblResource.ResourceAccessResponseDto;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: GetResourceAccessUseCase
 *   Descripción: Puerto de entrada para consultar la matriz de accesos y recursos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial en módulo resource
 *----------------------------------------
 */
public interface GetResourceAccessUseCase {
    ResourceAccessResponseDto getAccess(String mode, Long id);
}
