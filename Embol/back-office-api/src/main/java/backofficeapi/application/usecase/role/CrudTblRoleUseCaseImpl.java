package backofficeapi.application.usecase.role;

import backofficeapi.application.port.input.role.CrudTblRoleUseCase;
import backofficeapi.application.port.output.TblRoleRepositoryPort;
import backofficeapi.domain.enums.RoleStatus;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.model.TblRoleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public TblRoleModel createRole(TblRoleModel tblRolModel) {
        String normalizedNombre = tblRolModel.getSNombre().trim().replaceAll("\\s+", "_").toUpperCase();
        if (tblRoleRepositoryPort.existsRolByName(normalizedNombre)) {
            log.error("Error de negocio: ya existe un rol con el nombre: {}", normalizedNombre);
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Ya existe un rol con el nombre: " + normalizedNombre);
        }

        tblRolModel.setSNombre(normalizedNombre);
        tblRolModel.setSDescripcion(tblRolModel.getSDescripcion().trim());

        TblRoleModel saveRole = tblRoleRepositoryPort.saveRole(tblRolModel);

        if (saveRole == null || saveRole.getIIdRol() == null) {
            log.error("Error crítico: Error al guardar el role: {}", saveRole);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el role");
        }

        log.info("Rol creado exitosamente con ID: {}", saveRole.getIIdRol());
        return saveRole;
    }

    @Override
    @Transactional
    public TblRoleModel updateRole(Long id, TblRoleModel tblRolModel) {
        TblRoleModel existingRole = tblRoleRepositoryPort.findById(id).orElseThrow(() -> {
            log.error("Error de negocio: no existe el rol con ID: {}", id);
            return new BusinessApiException(HttpStatus.NOT_FOUND, "No se encontró el rol");
        });

        if (tblRolModel.getSNombre() != null) {
            String newNombre = tblRolModel.getSNombre().trim().replaceAll("\\s+", "_").toUpperCase();
            if (!newNombre.equalsIgnoreCase(existingRole.getSNombre())
                    && tblRoleRepositoryPort.existsRolByName(newNombre)) {
                log.error("Error de negocio: ya existe un rol con el nombre: {}", newNombre);
                throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Ya existe un rol con el nombre: " + newNombre);
            }
            existingRole.setSNombre(newNombre);
        }

        if (tblRolModel.getSDescripcion() != null) {
            existingRole.setSDescripcion(tblRolModel.getSDescripcion().trim());
        }

        if (tblRolModel.getSEstado() != null) {
            existingRole.setSEstado(tblRolModel.getSEstado());
        }

        TblRoleModel updatedRole = tblRoleRepositoryPort.saveRole(existingRole);

        if (updatedRole == null || updatedRole.getIIdRol() == null) {
            log.error("Error crítico: Error al actualizar el role: {}", existingRole);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el role");
        }

        log.info("Rol actualizado exitosamente con ID: {}", updatedRole.getIIdRol());
        return updatedRole;
    }

    @Override
    @Transactional
    public TblRoleModel deleteRole(Long id) {
        if (id == null) {
            log.error("Error de validación: el ID del role es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del role es requerido");
        }

        TblRoleModel existingRole = tblRoleRepositoryPort.findById(id).orElseThrow(() -> {
            log.error("Error de negocio: no existe el grupo con ID: {}", id);
            return new BusinessApiException(HttpStatus.NOT_FOUND, "No se encontro el rol");
        });

        existingRole.markAsDeleted();

        TblRoleModel deletedRole = tblRoleRepositoryPort.saveRole(existingRole);

        if (deletedRole == null || deletedRole.getIIdRol() == null) {
            log.error("Error crítico: Error al eliminar el role: {}", existingRole);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el role");
        }

        log.info("Rol eliminado exitosamente con ID: {}", deletedRole.getIIdRol());
        return deletedRole;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TblRoleModel> pageListRol(Pageable pageable) {
        Page<TblRoleModel> pageRol = tblRoleRepositoryPort.getPageListRol(pageable);
        return pageRol;
    }
}
