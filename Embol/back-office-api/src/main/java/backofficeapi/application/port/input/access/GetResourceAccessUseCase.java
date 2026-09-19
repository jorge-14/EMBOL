package backofficeapi.application.port.input.access;

import backofficeapi.infrastructure.adapter.input.rest.response.tblAccess.ResourceAccessResponseDto;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: GetResourceAccessUseCase
 *   Descripción: Puerto de entrada para consultar accesos de recursos y acciones
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
public interface GetResourceAccessUseCase {
    ResourceAccessResponseDto getAccess(String mode, Long id);
}
