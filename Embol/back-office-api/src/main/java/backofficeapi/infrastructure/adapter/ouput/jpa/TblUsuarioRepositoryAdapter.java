package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblUsuarioRepositoryPort;
import backofficeapi.domain.model.TblUsuarioModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblGrupo;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuario;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuarioGrupo;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuarioRol;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.TblUsuarioMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblGrupoRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblRolRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblUsuarioGrupoRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblUsuarioRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblUsuarioRolRepository;
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
 *   Código de Objeto: TblUsuarioRepositoryAdapter
 *   Descripción: Adaptador de persistencia JPA para TblUsuario (TBL_USUARIO) 
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Persistencia con entidades 
 *----------------------------------------
 */

@Service
@RequiredArgsConstructor
public class TblUsuarioRepositoryAdapter implements TblUsuarioRepositoryPort {

    private final TblUsuarioRepository tblUsuarioRepository;
    private final TblRolRepository tblRolRepository;
    private final TblGrupoRepository tblGrupoRepository;
    private final TblUsuarioRolRepository tblUsuarioRolRepository;
    private final TblUsuarioGrupoRepository tblUsuarioGrupoRepository;
    private final TblUsuarioMapper tblUsuarioMapper;

    @Override
    @Transactional
    public TblUsuarioModel save(TblUsuarioModel tblUsuarioModel) {
        TblUsuario tblUsuario = tblUsuarioMapper.toEntity(tblUsuarioModel);
        TblUsuario savedTblUsuario = tblUsuarioRepository.save(tblUsuario);

        List<TblUsuarioRol> savedRoles = new ArrayList<>();
        if (tblUsuarioModel.getRoleIds() != null) {
            tblUsuarioRolRepository.deleteByUsuario_Id(savedTblUsuario.getId());
            if (!tblUsuarioModel.getRoleIds().isEmpty()) {
                List<TblRol> roles = tblRolRepository.findAllById(tblUsuarioModel.getRoleIds());
                List<TblUsuarioRol> usuarioRoles = roles.stream()
                        .<TblUsuarioRol>map(rol -> TblUsuarioRol.builder()
                                .usuario(savedTblUsuario)
                                .rol(rol)
                                .build())
                        .toList();
                tblUsuarioRolRepository.saveAll(usuarioRoles);
                savedRoles = usuarioRoles;
            }
        } else {
            savedRoles = tblUsuarioRolRepository.findByUsuario_Id(savedTblUsuario.getId());
        }

        List<TblUsuarioGrupo> savedGrupos = new ArrayList<>();
        if (tblUsuarioModel.getGroupIds() != null) {
            tblUsuarioGrupoRepository.deleteByUsuario_Id(savedTblUsuario.getId());
            if (!tblUsuarioModel.getGroupIds().isEmpty()) {
                List<TblGrupo> grupos = tblGrupoRepository.findAllById(tblUsuarioModel.getGroupIds());
                List<TblUsuarioGrupo> usuarioGrupos = grupos.stream()
                        .<TblUsuarioGrupo>map(grupo -> TblUsuarioGrupo.builder()
                                .usuario(savedTblUsuario)
                                .grupo(grupo)
                                .build())
                        .toList();
                tblUsuarioGrupoRepository.saveAll(usuarioGrupos);
                savedGrupos = usuarioGrupos;
            }
        } else {
            savedGrupos = tblUsuarioGrupoRepository.findByUsuario_Id(savedTblUsuario.getId());
        }

        return tblUsuarioMapper.toModel(savedTblUsuario, savedRoles, savedGrupos);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblUsuarioModel> findById(Long id) {
        return tblUsuarioRepository.findById(id).map(this::mapWithRelations);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblUsuarioModel> findByUsername(String username) {
        return tblUsuarioRepository.findByUsernameIgnoreCase(username).map(this::mapWithRelations);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TblUsuarioModel> findByEmail(String email) {
        return tblUsuarioRepository.findByEmailIgnoreCase(email).map(this::mapWithRelations);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblUsuarioModel> findAll() {
        return tblUsuarioRepository.findByDeletedFalseOrderByNameAscLastnameAsc().stream()
                .map(this::mapWithRelations)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TblUsuarioModel> findPage(Pageable pageable) {
        return tblUsuarioRepository.findByDeletedFalse(pageable)
                .map(this::mapWithRelations);
    }

    @Override
    public boolean existsByUsername(String username) {
        return tblUsuarioRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return tblUsuarioRepository.existsByEmailIgnoreCase(email);
    }

    private TblUsuarioModel mapWithRelations(TblUsuario user) {
        List<TblUsuarioRol> usuarioRoles = tblUsuarioRolRepository.findByUsuario_Id(user.getId());
        List<TblUsuarioGrupo> usuarioGrupos = tblUsuarioGrupoRepository.findByUsuario_Id(user.getId());
        return tblUsuarioMapper.toModel(user, usuarioRoles, usuarioGrupos);
    }
}
