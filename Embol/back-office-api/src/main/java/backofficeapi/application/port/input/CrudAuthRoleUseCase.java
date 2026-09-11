package backofficeapi.application.port.input;

import backofficeapi.domain.model.AuthRoleModel;

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

public interface CrudAuthRoleUseCase {

    AuthRoleModel createRole(AuthRoleModel authRoleModel);
}
