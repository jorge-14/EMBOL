package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblRoleModel;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRoleRepositoryPort
 *   Descripción: Puerto de salida para persistencia de Role (TBL_ROL)
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

public interface TblRoleRepositoryPort {

    TblRoleModel saveRole(TblRoleModel tblRoleModel);

    List<TblRoleModel> listRole();
}
