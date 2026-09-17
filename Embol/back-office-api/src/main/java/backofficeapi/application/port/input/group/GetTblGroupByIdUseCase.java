package backofficeapi.application.port.input.group;

import backofficeapi.domain.model.TblGroupModel;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Marco Antonio Roca Montenegro
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   17.09.2026 | Marco Antonio Roca Montenegro | Creación Inicial
 *----------------------------------------
 */
public interface GetTblGroupByIdUseCase {
    TblGroupModel getGroupById(Long id);
}
