package backofficeapi.application.usecase.rol;

import backofficeapi.application.port.input.rol.ListRoleUseCase;
import backofficeapi.application.port.output.TblRolRepositoryPort;
import backofficeapi.domain.model.TblRolModel;
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

    private final TblRolRepositoryPort tblRolRepositoryPort;

    public ListRoleUseCaseImpl(TblRolRepositoryPort tblRolRepositoryPort){
        this.tblRolRepositoryPort = tblRolRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblRolModel> listRoleShort() {
        return tblRolRepositoryPort.listRole();
    }
}
