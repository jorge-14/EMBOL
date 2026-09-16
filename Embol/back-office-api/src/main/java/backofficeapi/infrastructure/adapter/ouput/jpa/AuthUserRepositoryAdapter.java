package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.AuthUserRepositoryPort;
import backofficeapi.domain.model.AuthUserModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthUser;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.AuthUserMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthUserRepositoryAdapter
 *   Descripción: Adaptador de persistencia JPA para AuthUser (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Actualización a estructura TBL_USUARIO y paginación
 *----------------------------------------
 */

@Service
@RequiredArgsConstructor
public class AuthUserRepositoryAdapter implements AuthUserRepositoryPort {

    private final AuthUserRepository authUserRepository;
    private final AuthUserMapper authUserMapper;

    @Override
    public AuthUserModel save(AuthUserModel authUserModel) {
        AuthUser authUser = authUserMapper.toEntity(authUserModel);
        AuthUser savedAuthUser = authUserRepository.save(authUser);
        return authUserMapper.toModel(savedAuthUser);
    }

    @Override
    public Optional<AuthUserModel> findById(Long id) {
        return authUserRepository.findById(id).map(authUserMapper::toModel);
    }

    @Override
    public Optional<AuthUserModel> findByUsername(String username) {
        return authUserRepository.findByUsernameIgnoreCase(username).map(authUserMapper::toModel);
    }

    @Override
    public Optional<AuthUserModel> findByEmail(String email) {
        return authUserRepository.findByEmailIgnoreCase(email).map(authUserMapper::toModel);
    }

    @Override
    public List<AuthUserModel> findAll() {
        return authUserRepository.findByDeletedFalseOrderByNameAscLastnameAsc().stream()
                .map(authUserMapper::toModel)
                .toList();
    }

    @Override
    public Page<AuthUserModel> findPage(Pageable pageable) {
        return authUserRepository.findByDeletedFalse(pageable)
                .map(authUserMapper::toModel);
    }

    @Override
    public boolean existsByUsername(String username) {
        return authUserRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return authUserRepository.existsByEmailIgnoreCase(email);
    }
}
