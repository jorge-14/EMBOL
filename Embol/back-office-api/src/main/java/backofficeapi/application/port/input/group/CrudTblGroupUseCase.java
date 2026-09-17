package backofficeapi.application.port.input.group;

import backofficeapi.domain.model.TblGroupModel;
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
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */
public interface CrudTblGroupUseCase {

    TblGroupModel createGroup(TblGroupModel tblGroupModel);
    Page<TblGroupModel> pageListGroup(Pageable pageable);
}
