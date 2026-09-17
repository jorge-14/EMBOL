package backofficeapi.application.usecase.role;

import backofficeapi.application.port.input.role.ListRoleUseCase;
import backofficeapi.application.port.output.TblRoleRepositoryPort;
import backofficeapi.domain.model.TblRoleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@Service
public class ListRoleUseCaseImpl implements ListRoleUseCase {

    private final TblRoleRepositoryPort tblRoleRepositoryPort;

    public ListRoleUseCaseImpl(TblRoleRepositoryPort tblRoleRepositoryPort) {
        this.tblRoleRepositoryPort = tblRoleRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblRoleModel> listRoleShort() {
        return tblRoleRepositoryPort.listRole();
    }
}
