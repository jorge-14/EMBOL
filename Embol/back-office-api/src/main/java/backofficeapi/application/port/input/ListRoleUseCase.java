package backofficeapi.application.port.input;

import backofficeapi.domain.model.TblRoleModel;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ListRoleUseCase
 *   Descripción: Puerto de entrada para listar roles de forma simplificada
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */
public interface ListRoleUseCase {

    List<TblRoleModel> listRoleShort();
}
