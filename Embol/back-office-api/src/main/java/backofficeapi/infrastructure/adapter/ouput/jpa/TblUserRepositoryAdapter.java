package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblUserRepositoryPort;
import backofficeapi.domain.model.TblUserModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblGrupo;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuario;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuarioGrupo;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuarioRol;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.TblUserMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblGroupRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblRoleRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblUserGroupRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblUserRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUserRepositoryAdapter
 *   Descripción: Adaptador de persistencia JPA para TblUser (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Persistencia con entidades
 *----------------------------------------
 */

@Service
@RequiredArgsConstructor
public class TblUserRepositoryAdapter implements TblUserRepositoryPort {

    private final TblUserRepository tblUserRepository;
    private final TblRoleRepository tblRoleRepository;
    private final TblGroupRepository tblGroupRepository;
    private final TblUserRoleRepository tblUserRoleRepository;
    private final TblUserGroupRepository tblUserGroupRepository;
    private final TblUserMapper tblUserMapper;

    @Override
    @Transactional
    public TblUserModel save(TblUserModel tblUserModel) {
        TblUsuario tblUsuario = tblUserMapper.toEntity(tblUserModel);
        TblUsuario savedTblUsuario = tblUserRepository.save(tblUsuario);

        List<TblUsuarioRol> savedRoles = new ArrayList<>();
        if (tblUserModel.getRoleIds() != null) {
            tblUserRoleRepository.deleteByUsuario_Id(savedTblUsuario.getId());
            if (!tblUserModel.getRoleIds().isEmpty()) {
                List<TblRol> roles = tblRoleRepository.findAllById(tblUserModel.getRoleIds());
                List<TblUsuarioRol> usuarioRoles = roles.stream()
                        .<TblUsuarioRol>map(rol -> TblUsuarioRol.builder()
                                .usuario(savedTblUsuario)
                                .rol(rol)
                                .build())
                        .toList();
                tblUserRoleRepository.saveAll(usuarioRoles);
                savedRoles = usuarioRoles;
            }
        } else {
            savedRoles = tblUserRoleRepository.findByUsuario_Id(savedTblUsuario.getId());
        }

        List<TblUsuarioGrupo> savedGrupos = new ArrayList<>();
        if (tblUserModel.getGroupIds() != null) {
            tblUserGroupRepository.deleteByUsuario_Id(savedTblUsuario.getId());
            if (!tblUserModel.getGroupIds().isEmpty()) {
                List<TblGrupo> grupos = tblGroupRepository.findAllById(tblUserModel.getGroupIds());
                List<TblUsuarioGrupo> usuarioGrupos = grupos.stream()
                        .<TblUsuarioGrupo>map(grupo -> TblUsuarioGrupo.builder()
                                .usuario(savedTblUsuario)
                                .grupo(grupo)
                                .build())
                        .toList();
                tblUserGroupRepository.saveAll(usuarioGrupos);
                savedGrupos = usuarioGrupos;
            }
        } else {
            savedGrupos = tblUserGroupRepository.findByUsuario_Id(savedTblUsuario.getId());
        }

        return tblUserMapper.toModel(savedTblUsuario, savedRoles, savedGrupos);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblUserModel> findById(Long id) {
        return tblUserRepository.findById(id).map(this::mapWithRelations);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblUserModel> findByUsername(String username) {
        return tblUserRepository.findByUsernameIgnoreCase(username).map(this::mapWithRelations);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblUserModel> findByEmail(String email) {
        return tblUserRepository.findByEmailIgnoreCase(email).map(this::mapWithRelations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblUserModel> findAll() {
        return tblUserRepository.findByDeletedFalseOrderByNameAscLastnameAsc().stream()
                .map(this::mapWithRelations)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TblUserModel> findPage(Pageable pageable) {
        return tblUserRepository.findByDeletedFalse(pageable)
                .map(this::mapWithRelations);
    }

    @Override
    public boolean existsByUsername(String username) {
        return tblUserRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return tblUserRepository.existsByEmailIgnoreCase(email);
    }

    private TblUserModel mapWithRelations(TblUsuario user) {
        List<TblUsuarioRol> usuarioRoles = tblUserRoleRepository.findByUsuario_Id(user.getId());
        List<TblUsuarioGrupo> usuarioGrupos = tblUserGroupRepository.findByUsuario_Id(user.getId());
        return tblUserMapper.toModel(user, usuarioRoles, usuarioGrupos);
    }
}
