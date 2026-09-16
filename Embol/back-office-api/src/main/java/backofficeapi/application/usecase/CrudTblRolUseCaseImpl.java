package backofficeapi.application.usecase;

import backofficeapi.application.port.input.CrudTblRolUseCase;
import backofficeapi.application.port.output.TblRolRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.model.TblRolModel;
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
public class CrudTblRolUseCaseImpl implements CrudTblRolUseCase {

    private final TblRolRepositoryPort tblRolRepositoryPort;

    public CrudTblRolUseCaseImpl(TblRolRepositoryPort tblRolRepositoryPort){
        this.tblRolRepositoryPort = tblRolRepositoryPort;
    }

    @Override
    @Transactional
    public TblRolModel createRole(TblRolModel tblRolModel) {

        if (tblRolModel.getSNombre() == null || tblRolModel.getSNombre().trim().isEmpty()) {
            log.error("Error de validación: el nombre es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre del role es requerido");
        }

        if (tblRolModel.getSDescripcion() == null || tblRolModel.getSDescripcion().trim().isEmpty()) {
            log.error("Error de validación: la descripción es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "La descripcion del role es requerido");
        }

        if (tblRolModel.getSEstado() == null) {
            log.error("Error de validación: el estado es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El estado del role es requerido");
        }

        TblRolModel saveRole = tblRolRepositoryPort.saveRole(tblRolModel);

        if (saveRole == null || saveRole.getIIdRol() == null) {
            log.error("Error crítico: Error al guardar el role: {}", saveRole);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el role");
        }
        return saveRole;
    }
}
