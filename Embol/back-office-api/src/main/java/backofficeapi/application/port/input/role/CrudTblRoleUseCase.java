package backofficeapi.application.port.input.role;

import backofficeapi.domain.model.TblRoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    TblRoleModel createRole(TblRoleModel tblRolModel);

    TblRoleModel updateRole(Long id, TblRoleModel tblRolModel);

    TblRoleModel deleteRole(Long id);

    Page<TblRoleModel> pageListRol(Pageable pageable);
}
