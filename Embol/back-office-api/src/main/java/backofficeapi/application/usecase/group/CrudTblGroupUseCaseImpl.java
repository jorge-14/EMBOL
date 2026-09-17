package backofficeapi.application.usecase.group;

import backofficeapi.application.port.input.group.CrudTblGroupUseCase;
import backofficeapi.application.port.output.TblGroupRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.model.TblGroupModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        if (tblGroupModel.getSDescripcion() == null || tblGroupModel.getSDescripcion().isEmpty()) {
            log.error("Error de validación: la descripción del grupo es requerida");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "La descripción del grupo es requerida");
        }

        TblGroupModel savedGroup = tblGroupRepositoryPort.saveGroup(tblGroupModel);

        if (savedGroup == null || savedGroup.getIIdGrupo() == null) {
            log.error("Error crítico: el grupo no se guardó correctamente: {}", tblGroupModel.getSNombre());
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el grupo: no se generó ID");
        }
        return savedGroup;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TblGroupModel> pageListGroup(Pageable pageable) {
        Page<TblGroupModel> pageListGroup = tblGroupRepositoryPort.getPageListGroup(pageable);
        return pageListGroup;
    }

    @Override
    @Transactional
    public TblGroupModel updateGroup(Long id, TblGroupModel groupModel) {

        TblGroupModel tblGroupModel = tblGroupRepositoryPort.getGroupById(id).orElseThrow(() -> {
            log.error("Error de negocio: no existe el grupo con ID: {}", id);
            return new BusinessApiException(HttpStatus.NOT_FOUND, "No se encontro el grupo");
        });

        if (groupModel.getSNombre() == null || groupModel.getSNombre().isEmpty()) {
            log.error("Error de validación: el nombre del grupo es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre del grupo es requerido");
        }

        if (groupModel.getSDescripcion() == null || groupModel.getSDescripcion().isEmpty()) {
            log.error("Error de validación: la descripción del grupo es requerida");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "La descripción del grupo es requerida");
        }

        tblGroupModel.setSNombre(groupModel.getSNombre() != null ? groupModel.getSNombre().trim() : null);
        tblGroupModel.setSDescripcion(groupModel.getSDescripcion() != null ? groupModel.getSDescripcion(): null);

        TblGroupModel savedGroup = tblGroupRepositoryPort.saveGroup(tblGroupModel);

        if (savedGroup == null || savedGroup.getIIdGrupo() == null) {
            log.error("Error crítico: el grupo no se guardó correctamente: {}", tblGroupModel.getSNombre());
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el grupo: no se generó ID");
        }
        return savedGroup;
    }

    @Override
    @Transactional
    public Boolean deleteGroup(Long id) {
        TblGroupModel tblGroupModel = tblGroupRepositoryPort.getGroupById(id).orElseThrow(() -> {
            log.error("Error de negocio: no existe el grupo con ID: {}", id);
            return new BusinessApiException(HttpStatus.NOT_FOUND, "No se encontro el grupo");
        });

        tblGroupModel.markAsDeleted();
        TblGroupModel savedGroup = tblGroupRepositoryPort.saveGroup(tblGroupModel);

        if (savedGroup == null || savedGroup.getIIdGrupo() == null) {
            log.error("Error crítico: el grupo no se guardó correctamente: {}", tblGroupModel.getSNombre());
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el grupo: no se generó ID");
        }
        return true;
    }
}
