package backofficeapi.application.usecase.group;

import backofficeapi.application.port.input.group.GetTblGroupByIdUseCase;
import backofficeapi.application.port.output.TblGroupRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.model.TblGroupModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Marco Antonio Roca Montenegro
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   17.09.2026 | Marco Antonio Roca Montenegro | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@Service
public class GetTblGroupByIdUseCaseImpl implements GetTblGroupByIdUseCase {

    private final TblGroupRepositoryPort tblGroupRepositoryPort;

    public GetTblGroupByIdUseCaseImpl(TblGroupRepositoryPort tblGroupRepositoryPort) {
        this.tblGroupRepositoryPort = tblGroupRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public TblGroupModel getGroupById(Long id) {
        return tblGroupRepositoryPort.getGroupById(id).orElseThrow(() -> {
            log.error("Error de negocio: no existe el grupo con ID: {}", id);
            return new BusinessApiException(HttpStatus.NOT_FOUND, "No se encontro el grupo");
        });
    }
}
