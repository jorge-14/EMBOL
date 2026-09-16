package backofficeapi.application.usecase;

import backofficeapi.application.port.input.CrudTblRegionalUseCase;
import backofficeapi.application.port.output.TblRegionalRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TechnicalApiException;
import backofficeapi.domain.exception.TblRegionalNotFoundException;
import backofficeapi.domain.model.TblRegionalModel;
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
 *   Código de Objeto: CrudTblRegionalUseCaseImpl
 *   Descripción: Implementación del caso de uso CRUD para TblRegional (TBL_REGIONAL)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class CrudTblRegionalUseCaseImpl implements CrudTblRegionalUseCase {

    private final TblRegionalRepositoryPort tblRegionalRepositoryPort;

    @Override
    @Transactional
    public TblRegionalModel createRegional(TblRegionalModel model) {
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

        tblRegionalRepositoryPort.getRegionalByCode(model.getCode().trim().toUpperCase())
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

        TblRegionalModel saved = tblRegionalRepositoryPort.saveRegional(model);
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
    public List<TblRegionalModel> listAllRegionals() {
        List<TblRegionalModel> list = tblRegionalRepositoryPort.listAllRegionals();
        log.info("Se encontraron {} regionales registradas", list.size());
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblRegionalModel> getRegionalById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID de la regional es requerido");
        }
        return tblRegionalRepositoryPort.getRegionalById(id);
    }

    @Override
    @Transactional
    public TblRegionalModel updateRegional(Long id, TblRegionalModel model) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID de la regional es requerido");
        }
        if (model == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST,
                    "Los datos de la regional a actualizar son requeridos");
        }

        TblRegionalModel existing = tblRegionalRepositoryPort.getRegionalById(id)
                .orElseThrow(() -> new TblRegionalNotFoundException(id));

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

        TblRegionalModel updated = tblRegionalRepositoryPort.saveRegional(existing);
        log.info("Regional actualizada exitosamente con ID: {}", updated.getId());
        return updated;
    }

    @Override
    @Transactional
    public void deleteRegionalById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID de la regional es requerido");
        }
        TblRegionalModel existing = tblRegionalRepositoryPort.getRegionalById(id)
                .orElseThrow(() -> new TblRegionalNotFoundException(id));

        existing.setDeleted(true);
        tblRegionalRepositoryPort.saveRegional(existing);
        log.info("Regional eliminada lógicamente (soft delete, deleted=true) con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TblRegionalModel> getListPageRegional(Pageable pageable) {
        return tblRegionalRepositoryPort.getListPageRegional(pageable);
    }
}
