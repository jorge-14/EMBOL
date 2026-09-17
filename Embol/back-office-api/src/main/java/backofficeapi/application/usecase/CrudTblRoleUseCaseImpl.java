package backofficeapi.application.usecase;

import backofficeapi.application.port.input.CrudTblRoleUseCase;
import backofficeapi.application.port.output.TblRoleRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.model.TblRoleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: CrudTblRoleUseCaseImpl
 *   Descripción: Implementación del caso de uso CRUD para Role
 *   Author Prog: Jorge Luis Choque Callizaya 
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@Service
public class CrudTblRoleUseCaseImpl implements CrudTblRoleUseCase {

    private final TblRoleRepositoryPort tblRoleRepositoryPort;

    public CrudTblRoleUseCaseImpl(TblRoleRepositoryPort tblRoleRepositoryPort) {
        this.tblRoleRepositoryPort = tblRoleRepositoryPort;
    }

    @Override
    @Transactional
    public TblRoleModel createRole(TblRoleModel tblRoleModel) {

        if (tblRoleModel.getSNombre() == null || tblRoleModel.getSNombre().trim().isEmpty()) {
            log.error("Error de validación: el nombre es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre del role es requerido");
        }

        if (tblRoleModel.getSDescripcion() == null || tblRoleModel.getSDescripcion().trim().isEmpty()) {
            log.error("Error de validación: la descripción es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "La descripcion del role es requerido");
        }

        if (tblRoleModel.getSEstado() == null) {
            log.error("Error de validación: el estado es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El estado del role es requerido");
        }

        TblRoleModel saveRole = tblRoleRepositoryPort.saveRole(tblRoleModel);

        if (saveRole == null || saveRole.getIIdRol() == null) {
            log.error("Error crítico: Error al guardar el role: {}", saveRole);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el role");
        }
        return saveRole;
    }
}
