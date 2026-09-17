package backofficeapi.application.port.input.user;

import backofficeapi.domain.model.TblUserModel;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ChangeStatusUserUseCase
 *   Descripción: Puerto de entrada para casos de uso de cambio de estado de Usuario
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   17.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

public interface ChangeStatusUserUseCase {

    TblUserModel activateUserById(Long id);

    TblUserModel deactivateUserById(Long id);
}
