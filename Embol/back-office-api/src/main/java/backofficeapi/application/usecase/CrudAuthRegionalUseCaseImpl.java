package backofficeapi.application.usecase;

import backofficeapi.application.port.input.CrudAuthRegionalUseCase;
import backofficeapi.application.port.output.AuthRegionalRepositoryPort;
import backofficeapi.domain.exception.AuthRegionalNotFoundException;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.model.AuthRegionalModel;
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
 *   Código de Objeto: CrudAuthRegionalUseCaseImpl
 *   Descripción: Implementación del caso de uso CRUD para AuthRegional
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class CrudAuthRegionalUseCaseImpl implements CrudAuthRegionalUseCase {

    private final AuthRegionalRepositoryPort authRegionalRepositoryPort;

    @Override
    @Transactional
    public AuthRegionalModel createRegional(AuthRegionalModel model) {
        if (model == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "Los datos de la regional son requeridos");
        }
        if (model.getCode() == null || model.getCode().trim().isEmpty()) {
            log.error("Error de validación: el código de la regional es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El código de la regional es requerido");
        }
        if (model.getName() == null || model.getName().trim().isEmpty()) {
            log.error("Error de validación: el nombre de la regional es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El nombre de la regional es requerido");
        }

        authRegionalRepositoryPort.getRegionalByCode(model.getCode().trim().toUpperCase())
                .ifPresent(existing -> {
                    log.error("Error: ya existe una regional con el código {}", model.getCode());
                    throw new BusinessApiException(HttpStatus.CONFLICT,
                            "Ya existe una regional con el código " + model.getCode());
                });

        model.setCode(model.getCode().trim().toUpperCase());
        model.setName(model.getName().trim());
        if (model.getAddress() != null) {
            model.setAddress(model.getAddress().trim());
        }
        model.setDeleted(false);

        AuthRegionalModel saved = authRegionalRepositoryPort.saveRegional(model);
        if (saved == null || saved.getId() == null) {
            log.error("Error crítico al guardar la regional: {}", model.getCode());
            throw new TechnicalApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al guardar la regional: no se generó ID");
        }
        log.info("Regional creada exitosamente con ID: {} y Código: {}", saved.getId(), saved.getCode());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthRegionalModel> listAllRegionals() {
        List<AuthRegionalModel> list = authRegionalRepositoryPort.listAllRegionals();
        log.info("Se encontraron {} regionales registradas", list.size());
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthRegionalModel> getRegionalById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID de la regional es requerido");
        }
        return authRegionalRepositoryPort.getRegionalById(id);
    }

    @Override
    @Transactional
    public AuthRegionalModel updateRegional(Long id, AuthRegionalModel model) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID de la regional es requerido");
        }
        if (model == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST,
                    "Los datos de la regional a actualizar son requeridos");
        }

        AuthRegionalModel existing = authRegionalRepositoryPort.getRegionalById(id)
                .orElseThrow(() -> new AuthRegionalNotFoundException(id));

        if (model.getName() != null && !model.getName().trim().isEmpty()) {
            existing.setName(model.getName().trim());
        }
        if (model.getDescription() != null) {
            existing.setDescription(model.getDescription().trim());
        }
        if (model.getAddress() != null) {
            existing.setAddress(model.getAddress().trim());
        }
        if (model.getDeleted() != null) {
            existing.setDeleted(model.getDeleted());
        }

        AuthRegionalModel updated = authRegionalRepositoryPort.saveRegional(existing);
        log.info("Regional actualizada exitosamente con ID: {}", updated.getId());
        return updated;
    }

    @Override
    @Transactional
    public void deleteRegionalById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID de la regional es requerido");
        }
        AuthRegionalModel existing = authRegionalRepositoryPort.getRegionalById(id)
                .orElseThrow(() -> new AuthRegionalNotFoundException(id));

        existing.setDeleted(true);
        authRegionalRepositoryPort.saveRegional(existing);
        log.info("Regional eliminada lógicamente (soft delete, deleted=true) con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuthRegionalModel> getListPageRegional(Pageable pageable) {
        return authRegionalRepositoryPort.getListPageRegional(pageable);
    }
}
