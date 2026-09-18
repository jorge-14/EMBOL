package backofficeapi.application.usecase.user;

import backofficeapi.application.port.input.user.ChangeStatusUserUseCase;
import backofficeapi.application.port.output.TblUserRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.model.TblUserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ChangeStatusUserUseCaseImpl
 *   Descripción: Implementación del caso de uso de activación y desactivación de Usuario
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   17.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ChangeStatusUserUseCaseImpl implements ChangeStatusUserUseCase {

    private final TblUserRepositoryPort tblUserRepositoryPort;

    @Override
    @Transactional
    public TblUserModel activateUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        TblUserModel existing = tblUserRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessApiException(HttpStatus.NOT_FOUND, "No se encontró el usuario con ID: " + id));

        existing.activate();
        TblUserModel saved = tblUserRepositoryPort.save(existing);
        log.info("Usuario activado con ID: {}", id);
        return saved;
    }

    @Override
    @Transactional
    public TblUserModel deactivateUserById(Long id) {
        if (id == null) {
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del usuario es requerido");
        }
        TblUserModel existing = tblUserRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessApiException(HttpStatus.NOT_FOUND, "No se encontró el usuario con ID: " + id));

        existing.deactivate();
        TblUserModel saved = tblUserRepositoryPort.save(existing);
        log.info("Usuario desactivado con ID: {}", id);
        return saved;
    }
}
