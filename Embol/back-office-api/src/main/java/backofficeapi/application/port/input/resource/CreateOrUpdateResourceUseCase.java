package backofficeapi.application.port.input.resource;

import backofficeapi.domain.model.TblResourceModel;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: CreateOrUpdateResourceUseCase
 *   Descripción: Puerto de entrada para crear o actualizar un recurso y vincular sus acciones permitidas
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial usando modelo de dominio
 *----------------------------------------
 */
public interface CreateOrUpdateResourceUseCase {

    TblResourceModel createOrUpdateResource(
            String name,
            String description,
            String icon,
            TblResourceModel parentResource,
            String[] actionCodes);
}
