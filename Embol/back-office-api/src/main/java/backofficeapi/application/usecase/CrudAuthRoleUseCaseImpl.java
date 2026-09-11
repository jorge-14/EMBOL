package backofficeapi.application.usecase;

import backofficeapi.application.port.input.CrudAuthRoleUseCase;
import backofficeapi.application.port.output.AuthRoleRepositoryPort;
import backofficeapi.domain.model.AuthRoleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Slf4j
@Service
public class CrudAuthRoleUseCaseImpl implements CrudAuthRoleUseCase {

    private final AuthRoleRepositoryPort authRoleRepositoryPort;

    public CrudAuthRoleUseCaseImpl(AuthRoleRepositoryPort authRoleRepositoryPort){
        this.authRoleRepositoryPort = authRoleRepositoryPort;
    }

    @Override
    @Transactional
    public AuthRoleModel createRole(AuthRoleModel authRoleModel) {
        AuthRoleModel saveRole = authRoleRepositoryPort.saveRole(authRoleModel);
        return saveRole;
    }
}
