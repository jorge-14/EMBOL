package backofficeapi.application.port.input.role;

import backofficeapi.domain.model.TblRoleModel;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: GetRoleByIdUseCase
 *   Descripción: Puerto de entrada para obtener la información de un rol por ID
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   17.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */
public interface GetRoleByIdUseCase {

    TblRoleModel getRoleById(Long id);
}
