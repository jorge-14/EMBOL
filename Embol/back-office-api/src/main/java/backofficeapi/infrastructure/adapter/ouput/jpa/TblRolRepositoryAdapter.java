package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblRolRepositoryPort;
import backofficeapi.domain.model.TblRolModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.TblRolMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.AuthRoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Component
public class TblRolRepositoryAdapter implements TblRolRepositoryPort {

    private final AuthRoleRepository authRoleRepository;
    private final TblRolMapper tblRolMapper;

    public TblRolRepositoryAdapter(AuthRoleRepository authRoleRepository, TblRolMapper tblRolMapper) {
        this.authRoleRepository = authRoleRepository;
        this.tblRolMapper = tblRolMapper;
    }

    @Override
    @Transactional
    public TblRolModel saveRole(TblRolModel tblRolModel) {
        TblRol authRole = tblRolMapper.toEntity(tblRolModel);
        TblRol authRoleSave = authRoleRepository.save(authRole);
        return tblRolMapper.toModel(authRoleSave);
    }
}
