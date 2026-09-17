package backofficeapi.application.port.input;

import backofficeapi.domain.model.TblRoleModel;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: CrudTblRoleUseCase
 *   Descripción: Puerto de entrada para casos de uso CRUD de Role
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

public interface CrudTblRoleUseCase {

    TblRoleModel createRole(TblRoleModel tblRoleModel);
}
