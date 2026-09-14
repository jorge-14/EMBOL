package backofficeapi.application.usecase;

import backofficeapi.application.port.input.CrudAuthRoleUseCase;
import backofficeapi.application.port.output.AuthRoleRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.model.AuthRoleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

        if (authRoleModel.getName() == null || authRoleModel.getName().trim().isEmpty()) {
            log.error("Error de validación: el nombre es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre del role es requerido");
        }

        if (authRoleModel.getDescription() == null || authRoleModel.getDescription().trim().isEmpty()) {
            log.error("Error de validación: la descripción es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "La descripcion del role es requerido");
        }

        if (authRoleModel.getRoleStatus() == null) {
            log.error("Error de validación: el estado es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El estado del role es requerido");
        }

        AuthRoleModel saveRole = authRoleRepositoryPort.saveRole(authRoleModel);

        if (saveRole == null || saveRole.getId() == null) {
            log.error("Error crítico: Error al guardar el role: {}", saveRole);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el role");
        }
        return saveRole;
    }
}
