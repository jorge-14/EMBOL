package backofficeapi.application.usecase;

import backofficeapi.application.port.input.CrudTblUsuarioUseCase;
import backofficeapi.application.port.output.TblUsuarioRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.exception.TblUsuarioAlreadyExistsException;
import backofficeapi.domain.exception.TblUsuarioNotFoundException;
import backofficeapi.domain.model.TblUsuarioModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: CrudTblUsuarioUseCaseImpl
 *   Descripción: Implementación del caso de uso CRUD para TblUsuario (TBL_USUARIO) con roles y grupos
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Manejo de roles y grupos asociados
 *----------------------------------------
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CrudTblUsuarioUseCaseImpl implements CrudTblUsuarioUseCase {

    private final TblUsuarioRepositoryPort tblUsuarioRepositoryPort;

    @Override
    @Transactional
    public TblUsuarioModel createUser(TblUsuarioModel tblUsuarioModel) {
        if (tblUsuarioModel == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Los datos del usuario son requeridos");
        }
        if (tblUsuarioModel.getUsername() == null || tblUsuarioModel.getUsername().trim().isEmpty()) {
            log.error("Error de validación: el nombre de usuario es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre de usuario es requerido");
        }
        if (tblUsuarioModel.getName() == null || tblUsuarioModel.getName().trim().isEmpty()) {
            log.error("Error de validación: el nombre es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre es requerido");
        }
        if (tblUsuarioModel.getLastname() == null || tblUsuarioModel.getLastname().trim().isEmpty()) {
            log.error("Error de validación: el apellido es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El apellido es requerido");
        }

        String normalizedUsername = tblUsuarioModel.getUsername().trim();
        if (tblUsuarioRepositoryPort.existsByUsername(normalizedUsername)) {
            log.error("Error de negocio: ya existe un usuario con username: {}", normalizedUsername);
            throw new TblUsuarioAlreadyExistsException(normalizedUsername);
        }

        if (tblUsuarioModel.getEmail() != null && !tblUsuarioModel.getEmail().trim().isEmpty()) {
            String normalizedEmail = tblUsuarioModel.getEmail().trim().toLowerCase();
            if (tblUsuarioRepositoryPort.existsByEmail(normalizedEmail)) {
                log.error("Error de negocio: ya existe un usuario con correo: {}", normalizedEmail);
                throw new BusinessApiException(HttpStatus.CONFLICT, "Ya existe un usuario con el correo: " + normalizedEmail);
            }
            tblUsuarioModel.setEmail(normalizedEmail);
        } else {
            tblUsuarioModel.setEmail(null);
        }

        tblUsuarioModel.setUsername(normalizedUsername);
        tblUsuarioModel.setName(tblUsuarioModel.getName().trim());
        tblUsuarioModel.setLastname(tblUsuarioModel.getLastname().trim());
        tblUsuarioModel.setDeleted(false);

        TblUsuarioModel savedUser = tblUsuarioRepositoryPort.save(tblUsuarioModel);
        if (savedUser == null || savedUser.getId() == null) {
            log.error("Error crítico: No se generó ID al guardar el usuario: {}", normalizedUsername);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el usuario: no se generó ID");
        }
        log.info("Usuario creado exitosamente con ID: {} y username: {}", savedUser.getId(), savedUser.getUsername());
        return savedUser;
    }

    @Override
    @Transactional
    public TblUsuarioModel updateUser(Long id, TblUsuarioModel tblUsuarioModel) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        if (tblUsuarioModel == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Los datos del usuario a actualizar son requeridos");
        }

        TblUsuarioModel existingUser = tblUsuarioRepositoryPort.findById(id)
                .orElseThrow(() -> new TblUsuarioNotFoundException(id));

        if (tblUsuarioModel.getName() != null && !tblUsuarioModel.getName().trim().isEmpty()) {
            existingUser.setName(tblUsuarioModel.getName().trim());
        }
        if (tblUsuarioModel.getLastname() != null && !tblUsuarioModel.getLastname().trim().isEmpty()) {
            existingUser.setLastname(tblUsuarioModel.getLastname().trim());
        }
        if (tblUsuarioModel.getEmail() != null) {
            String normalizedEmail = tblUsuarioModel.getEmail().trim().toLowerCase();
            if (!normalizedEmail.isEmpty() && !normalizedEmail.equalsIgnoreCase(existingUser.getEmail())) {
                if (tblUsuarioRepositoryPort.existsByEmail(normalizedEmail)) {
                    log.error("Error de negocio: el correo ya se encuentra registrado por otro usuario: {}", normalizedEmail);
                    throw new BusinessApiException(HttpStatus.CONFLICT, "Ya existe un usuario con el correo: " + normalizedEmail);
                }
            }
            existingUser.setEmail(normalizedEmail.isEmpty() ? null : normalizedEmail);
        }
        if (tblUsuarioModel.getUserStatus() != null) {
            existingUser.setUserStatus(tblUsuarioModel.getUserStatus());
        }
        if (tblUsuarioModel.getRoleIds() != null) {
            existingUser.setRoleIds(tblUsuarioModel.getRoleIds());
        }
        if (tblUsuarioModel.getGroupIds() != null) {
            existingUser.setGroupIds(tblUsuarioModel.getGroupIds());
        }

        TblUsuarioModel updatedUser = tblUsuarioRepositoryPort.save(existingUser);
        log.info("Usuario actualizado exitosamente con ID: {}", updatedUser.getId());
        return updatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblUsuarioModel> getUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        return tblUsuarioRepositoryPort.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblUsuarioModel> getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre de usuario es requerido");
        }
        return tblUsuarioRepositoryPort.findByUsername(username.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblUsuarioModel> listAllUsers() {
        List<TblUsuarioModel> list = tblUsuarioRepositoryPort.findAll();
        log.info("Se encontraron {} usuarios registrados", list.size());
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TblUsuarioModel> getListPageUsers(Pageable pageable) {
        return tblUsuarioRepositoryPort.findPage(pageable);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        TblUsuarioModel existing = tblUsuarioRepositoryPort.findById(id)
                .orElseThrow(() -> new TblUsuarioNotFoundException(id));

        existing.delete();
        tblUsuarioRepositoryPort.save(existing);
        log.info("Usuario eliminado lógicamente (soft delete, deleted=true) con ID: {}", id);
    }

    @Override
    @Transactional
    public TblUsuarioModel activateUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        TblUsuarioModel existing = tblUsuarioRepositoryPort.findById(id)
                .orElseThrow(() -> new TblUsuarioNotFoundException(id));

        existing.activate();
        TblUsuarioModel saved = tblUsuarioRepositoryPort.save(existing);
        log.info("Usuario activado con ID: {}", id);
        return saved;
    }

    @Override
    @Transactional
    public TblUsuarioModel deactivateUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        TblUsuarioModel existing = tblUsuarioRepositoryPort.findById(id)
                .orElseThrow(() -> new TblUsuarioNotFoundException(id));

        existing.deactivate();
        TblUsuarioModel saved = tblUsuarioRepositoryPort.save(existing);
        log.info("Usuario desactivado con ID: {}", id);
        return saved;
    }
}
