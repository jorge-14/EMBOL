package backofficeapi.application.usecase;

import backofficeapi.application.port.input.CrudAuthUserUseCase;
import backofficeapi.application.port.output.AuthUserRepositoryPort;
import backofficeapi.domain.exception.AuthUserAlreadyExistsException;
import backofficeapi.domain.exception.AuthUserNotFoundException;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.model.AuthUserModel;
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
 *   Código de Objeto: CrudAuthUserUseCaseImpl
 *   Descripción: Implementación del caso de uso CRUD para AuthUser (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Actualización a estructura TBL_USUARIO, validaciones y paginación
 *----------------------------------------
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CrudAuthUserUseCaseImpl implements CrudAuthUserUseCase {

    private final AuthUserRepositoryPort authUserRepositoryPort;

    @Override
    @Transactional
    public AuthUserModel createUser(AuthUserModel authUserModel) {
        if (authUserModel == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Los datos del usuario son requeridos");
        }
        if (authUserModel.getUsername() == null || authUserModel.getUsername().trim().isEmpty()) {
            log.error("Error de validación: el nombre de usuario es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre de usuario es requerido");
        }
        if (authUserModel.getName() == null || authUserModel.getName().trim().isEmpty()) {
            log.error("Error de validación: el nombre es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre es requerido");
        }
        if (authUserModel.getLastname() == null || authUserModel.getLastname().trim().isEmpty()) {
            log.error("Error de validación: el apellido es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El apellido es requerido");
        }

        String normalizedUsername = authUserModel.getUsername().trim();
        if (authUserRepositoryPort.existsByUsername(normalizedUsername)) {
            log.error("Error de negocio: ya existe un usuario con username: {}", normalizedUsername);
            throw new AuthUserAlreadyExistsException(normalizedUsername);
        }

        if (authUserModel.getEmail() != null && !authUserModel.getEmail().trim().isEmpty()) {
            String normalizedEmail = authUserModel.getEmail().trim().toLowerCase();
            if (authUserRepositoryPort.existsByEmail(normalizedEmail)) {
                log.error("Error de negocio: ya existe un usuario con correo: {}", normalizedEmail);
                throw new BusinessApiException(HttpStatus.CONFLICT, "Ya existe un usuario con el correo: " + normalizedEmail);
            }
            authUserModel.setEmail(normalizedEmail);
        } else {
            authUserModel.setEmail(null);
        }

        authUserModel.setUsername(normalizedUsername);
        authUserModel.setName(authUserModel.getName().trim());
        authUserModel.setLastname(authUserModel.getLastname().trim());
        authUserModel.setDeleted(false);

        AuthUserModel savedUser = authUserRepositoryPort.save(authUserModel);
        if (savedUser == null || savedUser.getId() == null) {
            log.error("Error crítico: No se generó ID al guardar el usuario: {}", normalizedUsername);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el usuario: no se generó ID");
        }
        log.info("Usuario creado exitosamente con ID: {} y username: {}", savedUser.getId(), savedUser.getUsername());
        return savedUser;
    }

    @Override
    @Transactional
    public AuthUserModel updateUser(Long id, AuthUserModel authUserModel) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        if (authUserModel == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Los datos del usuario a actualizar son requeridos");
        }

        AuthUserModel existingUser = authUserRepositoryPort.findById(id)
                .orElseThrow(() -> new AuthUserNotFoundException(id));

        if (authUserModel.getName() != null && !authUserModel.getName().trim().isEmpty()) {
            existingUser.setName(authUserModel.getName().trim());
        }
        if (authUserModel.getLastname() != null && !authUserModel.getLastname().trim().isEmpty()) {
            existingUser.setLastname(authUserModel.getLastname().trim());
        }
        if (authUserModel.getEmail() != null) {
            String normalizedEmail = authUserModel.getEmail().trim().toLowerCase();
            if (!normalizedEmail.isEmpty() && !normalizedEmail.equalsIgnoreCase(existingUser.getEmail())) {
                if (authUserRepositoryPort.existsByEmail(normalizedEmail)) {
                    log.error("Error de negocio: el correo ya se encuentra registrado por otro usuario: {}", normalizedEmail);
                    throw new BusinessApiException(HttpStatus.CONFLICT, "Ya existe un usuario con el correo: " + normalizedEmail);
                }
            }
            existingUser.setEmail(normalizedEmail.isEmpty() ? null : normalizedEmail);
        }
        if (authUserModel.getUserStatus() != null) {
            existingUser.setUserStatus(authUserModel.getUserStatus());
        }

        AuthUserModel updatedUser = authUserRepositoryPort.save(existingUser);
        log.info("Usuario actualizado exitosamente con ID: {}", updatedUser.getId());
        return updatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthUserModel> getUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        return authUserRepositoryPort.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthUserModel> getUserByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre de usuario es requerido");
        }
        return authUserRepositoryPort.findByUsername(username.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthUserModel> listAllUsers() {
        List<AuthUserModel> list = authUserRepositoryPort.findAll();
        log.info("Se encontraron {} usuarios registrados", list.size());
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthUserModel> getListPageUsers(Pageable pageable) {
        return authUserRepositoryPort.findPage(pageable);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        AuthUserModel existing = authUserRepositoryPort.findById(id)
                .orElseThrow(() -> new AuthUserNotFoundException(id));

        existing.delete();
        authUserRepositoryPort.save(existing);
        log.info("Usuario eliminado lógicamente (soft delete, deleted=true) con ID: {}", id);
    }

    @Override
    @Transactional
    public AuthUserModel activateUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        AuthUserModel existing = authUserRepositoryPort.findById(id)
                .orElseThrow(() -> new AuthUserNotFoundException(id));

        existing.activate();
        AuthUserModel saved = authUserRepositoryPort.save(existing);
        log.info("Usuario activado con ID: {}", id);
        return saved;
    }

    @Override
    @Transactional
    public AuthUserModel deactivateUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        AuthUserModel existing = authUserRepositoryPort.findById(id)
                .orElseThrow(() -> new AuthUserNotFoundException(id));

        existing.deactivate();
        AuthUserModel saved = authUserRepositoryPort.save(existing);
        log.info("Usuario desactivado con ID: {}", id);
        return saved;
    }
}
