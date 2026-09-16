package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblUsuarioRepositoryPort;
import backofficeapi.domain.model.TblUsuarioModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblGrupo;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRol;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblUsuario;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.TblUsuarioMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblGrupoRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblRolRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUsuarioRepositoryAdapter
 *   Descripción: Adaptador de persistencia JPA para TblUsuario (TBL_USUARIO) con roles y grupos
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Persistencia de roles y grupos asociados
 *----------------------------------------
 */

@Service
@RequiredArgsConstructor
public class TblUsuarioRepositoryAdapter implements TblUsuarioRepositoryPort {

    private final TblUsuarioRepository tblUsuarioRepository;
    private final TblRolRepository tblRolRepository;
    private final TblGrupoRepository tblGrupoRepository;
    private final TblUsuarioMapper tblUsuarioMapper;

    @Override
    public TblUsuarioModel save(TblUsuarioModel tblUsuarioModel) {
        TblUsuario tblUsuario = tblUsuarioMapper.toEntity(tblUsuarioModel);

        if (tblUsuarioModel.getRoleIds() != null && !tblUsuarioModel.getRoleIds().isEmpty()) {
            List<TblRol> roles = tblRolRepository.findAllById(tblUsuarioModel.getRoleIds());
            tblUsuario.setRoles(new HashSet<>(roles));
        } else if (tblUsuarioModel.getRoleIds() != null) {
            tblUsuario.setRoles(new HashSet<>());
        }

        if (tblUsuarioModel.getGroupIds() != null && !tblUsuarioModel.getGroupIds().isEmpty()) {
            List<TblGrupo> grupos = tblGrupoRepository.findAllById(tblUsuarioModel.getGroupIds());
            tblUsuario.setGrupos(new HashSet<>(grupos));
        } else if (tblUsuarioModel.getGroupIds() != null) {
            tblUsuario.setGrupos(new HashSet<>());
        }

        TblUsuario savedTblUsuario = tblUsuarioRepository.save(tblUsuario);
        return tblUsuarioMapper.toModel(savedTblUsuario);
    }

    @Override
    public Optional<TblUsuarioModel> findById(Long id) {
        return tblUsuarioRepository.findById(id).map(tblUsuarioMapper::toModel);
    }

    @Override
    public Optional<TblUsuarioModel> findByUsername(String username) {
        return tblUsuarioRepository.findByUsernameIgnoreCase(username).map(tblUsuarioMapper::toModel);
    }

    @Override
    public Optional<TblUsuarioModel> findByEmail(String email) {
        return tblUsuarioRepository.findByEmailIgnoreCase(email).map(tblUsuarioMapper::toModel);
    }

    @Override
    public List<TblUsuarioModel> findAll() {
        return tblUsuarioRepository.findByDeletedFalseOrderByNameAscLastnameAsc().stream()
                .map(tblUsuarioMapper::toModel)
                .toList();
    }

    @Override
    public Page<TblUsuarioModel> findPage(Pageable pageable) {
        return tblUsuarioRepository.findByDeletedFalse(pageable)
                .map(tblUsuarioMapper::toModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return tblUsuarioRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return tblUsuarioRepository.existsByEmailIgnoreCase(email);
    }
}
