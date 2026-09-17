package backofficeapi.application.port.input;

import backofficeapi.domain.model.TblRolModel;
import java.util.List;

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
public interface ListRoleUseCase {

    List<TblRolModel> listRoleShort();
}
