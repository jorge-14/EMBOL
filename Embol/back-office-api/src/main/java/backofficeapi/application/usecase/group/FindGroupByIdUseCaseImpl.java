package backofficeapi.application.usecase.group;

import backofficeapi.application.port.input.group.FindGroupByIdUseCase;
import backofficeapi.application.port.output.TblGroupRepositoryPort;
import backofficeapi.domain.model.TblGroupModel;
import lombok.extern.slf4j.Slf4j;
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
 *   17.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@Service
public class FindGroupByIdUseCaseImpl implements FindGroupByIdUseCase {

    private final TblGroupRepositoryPort tblGroupRepositoryPort;

    public FindGroupByIdUseCaseImpl(TblGroupRepositoryPort tblGroupRepositoryPort) {
        this.tblGroupRepositoryPort = tblGroupRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public TblGroupModel getInformationGroupById(Long id) {
        TblGroupModel information = tblGroupRepositoryPort.getInformationById(id);
        return information;
    }
}
