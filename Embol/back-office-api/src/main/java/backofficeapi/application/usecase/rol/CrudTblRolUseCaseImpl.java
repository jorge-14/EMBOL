package backofficeapi.application.usecase.rol;

import backofficeapi.application.port.input.rol.CrudTblRolUseCase;
import backofficeapi.application.port.output.TblRolRepositoryPort;
import backofficeapi.domain.enums.SEstadoRol;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TblRolNotFoundException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.model.TblRolModel;
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

        if (tblRolModel == null) {
            log.error("Error de validación: los datos del role son requeridos");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Los datos del role son requeridos");
        }

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

        String normalizedNombre = tblRolModel.getSNombre().trim();
        if (tblRolRepositoryPort.existsRolByName(normalizedNombre)) {
            log.error("Error de negocio: ya existe un rol con el nombre: {}", normalizedNombre);
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Ya existe un rol con el nombre: " + normalizedNombre);
        }

        tblRolModel.setSNombre(normalizedNombre);
        tblRolModel.setSDescripcion(tblRolModel.getSDescripcion().trim());

        TblRolModel saveRole = tblRolRepositoryPort.saveRole(tblRolModel);

        if (saveRole == null || saveRole.getIIdRol() == null) {
            log.error("Error crítico: Error al guardar el role: {}", saveRole);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el role");
        }
        return saveRole;
    }

    @Override
    @Transactional
    public TblRolModel updateRole(Long id, TblRolModel tblRolModel) {
        if (id == null) {
            log.error("Error de validación: el ID del role es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del role es requerido");
        }

        if (tblRolModel == null) {
            log.error("Error de validación: los datos del role a actualizar son requeridos");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Los datos del role a actualizar son requeridos");
        }

        TblRolModel existingRole = tblRolRepositoryPort.findById(id)
                .orElseThrow(() -> new TblRolNotFoundException(id));

        if (tblRolModel.getSNombre() != null && !tblRolModel.getSNombre().trim().isEmpty()) {
            String newNombre = tblRolModel.getSNombre().trim();
            if (!newNombre.equalsIgnoreCase(existingRole.getSNombre())) {
                if (tblRolRepositoryPort.existsRolByName(newNombre)) {
                    log.error("Error de negocio: ya existe un rol con el nombre: {}", newNombre);
                    throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Ya existe un rol con el nombre: " + newNombre);
                }
            }
            existingRole.setSNombre(newNombre);
        }

        if (tblRolModel.getSDescripcion() != null && !tblRolModel.getSDescripcion().trim().isEmpty()) {
            existingRole.setSDescripcion(tblRolModel.getSDescripcion().trim());
        }

        if (tblRolModel.getSEstado() != null) {
            existingRole.setSEstado(tblRolModel.getSEstado());
        }

        TblRolModel updatedRole = tblRolRepositoryPort.saveRole(existingRole);

        if (updatedRole == null || updatedRole.getIIdRol() == null) {
            log.error("Error crítico: Error al actualizar el role: {}", existingRole);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el role");
        }

        log.info("Rol actualizado exitosamente con ID: {}", updatedRole.getIIdRol());
        return updatedRole;
    }

    @Override
    @Transactional
    public TblRolModel deleteRole(Long id) {
        if (id == null) {
            log.error("Error de validación: el ID del role es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del role es requerido");
        }

        TblRolModel existingRole = tblRolRepositoryPort.findById(id)
                .orElseThrow(() -> new TblRolNotFoundException(id));

        existingRole.setSEstado(SEstadoRol.DELETED);

        TblRolModel deletedRole = tblRolRepositoryPort.saveRole(existingRole);

        if (deletedRole == null || deletedRole.getIIdRol() == null) {
            log.error("Error crítico: Error al eliminar el role: {}", existingRole);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el role");
        }

        log.info("Rol eliminado exitosamente con ID: {}", deletedRole.getIIdRol());
        return deletedRole;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TblRolModel> pageListRol(Pageable pageable) {
        Page<TblRolModel> pageRol = tblRolRepositoryPort.getPageListRol(pageable);
        return pageRol;
    }
}
