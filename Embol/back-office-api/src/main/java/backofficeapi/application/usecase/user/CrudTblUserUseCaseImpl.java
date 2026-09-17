package backofficeapi.application.usecase.user;

import backofficeapi.application.port.input.user.CrudTblUserUseCase;
import backofficeapi.application.port.output.TblUserRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.exception.TblUserAlreadyExistsException;
import backofficeapi.domain.exception.TblUserNotFoundException;
import backofficeapi.domain.model.TblUserModel;
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
 *   Código de Objeto: CrudTblUserUseCaseImpl
 *   Descripción: Implementación de casos de uso CRUD para TblUser (TBL_USUARIO)
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
public class CrudTblUserUseCaseImpl implements CrudTblUserUseCase {

    private final TblUserRepositoryPort tblUserRepositoryPort;

    @Override
    @Transactional
    public TblUserModel createUser(TblUserModel tblUserModel) {
        if (tblUserModel == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Los datos del usuario son requeridos");
        }
        if (tblUserModel.getUsername() == null || tblUserModel.getUsername().trim().isEmpty()) {
            log.error("Error de validación: el nombre de usuario es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre de usuario es requerido");
        }
        if (tblUserModel.getName() == null || tblUserModel.getName().trim().isEmpty()) {
            log.error("Error de validación: el nombre es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre es requerido");
        }
        if (tblUserModel.getLastname() == null || tblUserModel.getLastname().trim().isEmpty()) {
            log.error("Error de validación: el apellido es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El apellido es requerido");
        }

        String normalizedUsername = tblUserModel.getUsername().trim();
        if (tblUserRepositoryPort.existsByUsername(normalizedUsername)) {
            log.error("Error de negocio: ya existe un usuario con username: {}", normalizedUsername);
            throw new TblUserAlreadyExistsException(normalizedUsername);
        }

        if (tblUserModel.getEmail() != null && !tblUserModel.getEmail().trim().isEmpty()) {
            String normalizedEmail = tblUserModel.getEmail().trim().toLowerCase();
            if (tblUserRepositoryPort.existsByEmail(normalizedEmail)) {
                log.error("Error de negocio: ya existe un usuario con correo: {}", normalizedEmail);
                throw new BusinessApiException(HttpStatus.CONFLICT,
                        "Ya existe un usuario con el correo: " + normalizedEmail);
            }
            tblUserModel.setEmail(normalizedEmail);
        } else {
            tblUserModel.setEmail(null);
        }

        tblUserModel.setUsername(normalizedUsername);
        tblUserModel.setName(tblUserModel.getName().trim());
        tblUserModel.setLastname(tblUserModel.getLastname().trim());
        tblUserModel.setDeleted(false);

        TblUserModel savedUser = tblUserRepositoryPort.save(tblUserModel);
        if (savedUser == null || savedUser.getId() == null) {
            log.error("Error crítico: No se generó ID al guardar el usuario: {}", normalizedUsername);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al guardar el usuario: no se generó ID");
        }
        log.info("Usuario creado exitosamente con ID: {} y username: {}", savedUser.getId(), savedUser.getUsername());
        return savedUser;
    }

    @Override
    @Transactional
    public TblUserModel updateUser(Long id, TblUserModel tblUserModel) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        if (tblUserModel == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Los datos del usuario a actualizar son requeridos");
        }

        TblUserModel existingUser = tblUserRepositoryPort.findById(id)
                .orElseThrow(() -> new TblUserNotFoundException(id));

        if (tblUserModel.getName() != null && !tblUserModel.getName().trim().isEmpty()) {
            existingUser.setName(tblUserModel.getName().trim());
        }
        if (tblUserModel.getLastname() != null && !tblUserModel.getLastname().trim().isEmpty()) {
            existingUser.setLastname(tblUserModel.getLastname().trim());
        }
        if (tblUserModel.getEmail() != null) {
            String normalizedEmail = tblUserModel.getEmail().trim().toLowerCase();
            if (!normalizedEmail.isEmpty() && !normalizedEmail.equalsIgnoreCase(existingUser.getEmail())) {
                if (tblUserRepositoryPort.existsByEmail(normalizedEmail)) {
                    log.error("Error de negocio: el correo ya se encuentra registrado por otro usuario: {}",
                            normalizedEmail);
                    throw new BusinessApiException(HttpStatus.CONFLICT,
                            "Ya existe un usuario con el correo: " + normalizedEmail);
                }
            }
            existingUser.setEmail(normalizedEmail.isEmpty() ? null : normalizedEmail);
        }
        if (tblUserModel.getUserStatus() != null) {
            existingUser.setUserStatus(tblUserModel.getUserStatus());
        }
        if (tblUserModel.getRoleIds() != null) {
            existingUser.setRoleIds(tblUserModel.getRoleIds());
        }
        if (tblUserModel.getGroupIds() != null) {
            existingUser.setGroupIds(tblUserModel.getGroupIds());
        }

        TblUserModel updatedUser = tblUserRepositoryPort.save(existingUser);
        log.info("Usuario actualizado exitosamente con ID: {}", updatedUser.getId());
        return updatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblUserModel> getUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        return tblUserRepositoryPort.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblUserModel> getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre de usuario es requerido");
        }
        return tblUserRepositoryPort.findByUsername(username.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblUserModel> listAllUsers() {
        List<TblUserModel> list = tblUserRepositoryPort.findAll();
        log.info("Se encontraron {} usuarios registrados", list.size());
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TblUserModel> getListPageUsers(Pageable pageable) {
        return tblUserRepositoryPort.findPage(pageable);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        TblUserModel existing = tblUserRepositoryPort.findById(id)
                .orElseThrow(() -> new TblUserNotFoundException(id));

        existing.delete();
        tblUserRepositoryPort.save(existing);
        log.info("Usuario eliminado lógicamente (soft delete, deleted=true) con ID: {}", id);
    }
}
