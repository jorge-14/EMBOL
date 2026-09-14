package backofficeapi.application.usecase;

import backofficeapi.application.port.input.CrudAuthUserUseCase;
import backofficeapi.application.port.output.AuthUserRepositoryPort;
import backofficeapi.domain.exception.AuthUserAlreadyExistsException;
import backofficeapi.domain.exception.AuthUserNotFoundException;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.model.AuthUserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
@Slf4j
@Service
public class CrudAuthUserUseCaseImpl implements CrudAuthUserUseCase {

    private final AuthUserRepositoryPort authUserRepositoryPort;

    public CrudAuthUserUseCaseImpl(AuthUserRepositoryPort authUserRepositoryPort) {
        this.authUserRepositoryPort = authUserRepositoryPort;
    }

    @Override
    @Transactional
    public AuthUserModel create(AuthUserModel authUserModel) {
        if (authUserModel.getEntraId() == null || authUserModel.getEntraId().trim().isEmpty()) {
            log.error("Error de validación: el entraId es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El entraId del usuario es requerido");
        }

        if (authUserRepositoryPort.existsByEntraId(authUserModel.getEntraId())) {
            log.error("Error de negocio: el usuario ya existe con entraId: {}", authUserModel.getEntraId());
            throw new AuthUserAlreadyExistsException(authUserModel.getEntraId());
        }

        AuthUserModel savedUser = authUserRepositoryPort.save(authUserModel);
        if (savedUser == null || savedUser.getId() == null) {
            log.error("Error crítico: Error al guardar el usuario: {}", savedUser);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el usuario");
        }
        return savedUser;
    }

    @Override
    @Transactional
    public AuthUserModel update(AuthUserModel authUserModel) {
        if (authUserModel.getEntraId() == null || authUserModel.getEntraId().trim().isEmpty()) {
            log.error("Error de validación: el entraId es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El entraId del usuario es requerido");
        }

        AuthUserModel existingUser = findByEntraId(authUserModel.getEntraId());
        existingUser.updateFromEntraId(
                authUserModel.getUsername(),
                authUserModel.getName(),
                authUserModel.getFatherLastname(),
                authUserModel.getMotherLastname()
        );

        AuthUserModel updatedUser = authUserRepositoryPort.save(existingUser);
        if (updatedUser == null || updatedUser.getId() == null) {
            log.error("Error crítico: Error al actualizar el usuario: {}", updatedUser);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el usuario");
        }
        return updatedUser;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthUserModel findByEntraId(String entraId) {
        if (entraId == null || entraId.trim().isEmpty()) {
            log.error("Error de validación: el entraId es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El entraId es requerido");
        }
        return authUserRepositoryPort.findByEntraId(entraId)
                .orElseThrow(() -> {
                    log.error("Error de negocio: usuario no encontrado con entraId: {}", entraId);
                    return new AuthUserNotFoundException(entraId);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public AuthUserModel findById(Long id) {
        if (id == null) {
            log.error("Error de validación: el id es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        return authUserRepositoryPort.findById(id)
                .orElseThrow(() -> {
                    log.error("Error de negocio: usuario no encontrado con id: {}", id);
                    return new AuthUserNotFoundException("id=" + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthUserModel> findAll() {
        return authUserRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public void delete(String entraId) {
        AuthUserModel authUserModel = findByEntraId(entraId);
        authUserModel.delete();
        AuthUserModel savedUser = authUserRepositoryPort.save(authUserModel);
        if (savedUser == null) {
            log.error("Error crítico: Error al eliminar el usuario: {}", entraId);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el usuario");
        }
    }

    @Override
    @Transactional
    public AuthUserModel activate(String entraId) {
        AuthUserModel authUserModel = findByEntraId(entraId);
        authUserModel.activate();
        AuthUserModel savedUser = authUserRepositoryPort.save(authUserModel);
        if (savedUser == null) {
            log.error("Error crítico: Error al activar el usuario: {}", entraId);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al activar el usuario");
        }
        return savedUser;
    }

    @Override
    @Transactional
    public AuthUserModel deactivate(String entraId) {
        AuthUserModel authUserModel = findByEntraId(entraId);
        authUserModel.deactivate();
        AuthUserModel savedUser = authUserRepositoryPort.save(authUserModel);
        if (savedUser == null) {
            log.error("Error crítico: Error al desactivar el usuario: {}", entraId);
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al desactivar el usuario");
        }
        return savedUser;
    }
}
