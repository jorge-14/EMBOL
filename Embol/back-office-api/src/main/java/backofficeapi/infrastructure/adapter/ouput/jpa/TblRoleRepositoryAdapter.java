package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblRoleRepositoryPort;
import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.TblRoleMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblRoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public TblRoleModel saveRole(TblRoleModel tblRolModel) {
        TblRol authRole = tblRoleMapper.toEntity(tblRolModel);
        TblRol authRoleSave = tblRoleRepository.save(authRole);
        return tblRoleMapper.toModel(authRoleSave);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblRoleModel> listRole() {
        return tblRoleRepository.listRole();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblRoleModel> findById(Long id) {
        return tblRoleRepository.findActiveById(id).map(tblRoleMapper::toModel);
    }

    @Override
    public Page<TblRoleModel> getPageListRol(Pageable pageable) {
        Page<TblRol> pageRole = tblRoleRepository.pageListGroup(pageable);
        return pageRole.map(tblRoleMapper::toModel);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsRolByName(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return tblRoleRepository.existsByName(nombre.trim());
    }
}
