package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblGroupModel;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */
public interface TblGroupRepositoryPort {
    TblGroupModel saveGroup(TblGroupModel tblGroupModel);
}
