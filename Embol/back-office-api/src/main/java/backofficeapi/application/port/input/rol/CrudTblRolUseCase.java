package backofficeapi.application.port.input.rol;

import backofficeapi.domain.model.TblRolModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

public interface CrudTblRolUseCase {

    TblRolModel createRole(TblRolModel tblRolModel);

    TblRolModel updateRole(Long id, TblRolModel tblRolModel);

    TblRolModel deleteRole(Long id);

    Page<TblRolModel> pageListRol(Pageable pageable);
}
