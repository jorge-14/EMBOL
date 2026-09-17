package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblRoleRepositoryPort;
import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.TblRoleMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblRoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRoleRepositoryAdapter
 *   Descripción: Adaptador de persistencia JPA para Role (TBL_ROL)
 *   Author Prog: Jorge Luis Choque Callizaya / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Component
public class TblRoleRepositoryAdapter implements TblRoleRepositoryPort {

    private final TblRoleRepository tblRoleRepository;
    private final TblRoleMapper tblRoleMapper;

    public TblRoleRepositoryAdapter(TblRoleRepository tblRoleRepository, TblRoleMapper tblRoleMapper) {
        this.tblRoleRepository = tblRoleRepository;
        this.tblRoleMapper = tblRoleMapper;
    }

    @Override
    @Transactional
    public TblRoleModel saveRole(TblRoleModel tblRoleModel) {
        TblRol authRole = tblRoleMapper.toEntity(tblRoleModel);
        TblRol authRoleSave = tblRoleRepository.save(authRole);
        return tblRoleMapper.toModel(authRoleSave);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblRoleModel> listRole() {
        return tblRoleRepository.listRole();
    }
}
