package backofficeapi.application.usecase.group;

import backofficeapi.application.port.input.group.CrudTblGroupUseCase;
import backofficeapi.application.port.output.TblGroupRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.model.TblGroupModel;
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
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@Service
public class CrudTblGroupUseCaseImpl implements CrudTblGroupUseCase {

    private final TblGroupRepositoryPort tblGroupRepositoryPort;

    public CrudTblGroupUseCaseImpl(TblGroupRepositoryPort tblGroupRepositoryPort) {
        this.tblGroupRepositoryPort = tblGroupRepositoryPort;
    }

    @Override
    @Transactional
    public TblGroupModel createGroup(TblGroupModel tblGroupModel) {

        if (tblGroupModel.getSNombre() == null || tblGroupModel.getSNombre().isEmpty()) {
            log.error("Error de validación: el nombre del grupo es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre del grupo es requerido");
        }

        if (tblGroupModel.getSNombre().length() > 40) {
            log.error("Error de validación: el nombre del grupo supera los 40 caracteres: {}", tblGroupModel.getSNombre());
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre del grupo no puede superar los 40 caracteres");
        }

        if (tblGroupModel.getSDescripcion() == null || tblGroupModel.getSDescripcion().isEmpty()) {
            log.error("Error de validación: la descripción del grupo es requerida");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "La descripción del grupo es requerida");
        }

        if (tblGroupModel.getSDescripcion().length() > 255) {
            log.error("Error de validación: la descripción del grupo supera los 255 caracteres");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "La descripción del grupo no puede superar los 255 caracteres");
        }

        TblGroupModel savedGroup = tblGroupRepositoryPort.saveGroup(tblGroupModel);

        if (savedGroup == null || savedGroup.getIIdGrupo() == null) {
            log.error("Error crítico: el grupo no se guardó correctamente: {}", tblGroupModel.getSNombre());
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el grupo: no se generó ID");
        }
        return savedGroup;
    }
}
