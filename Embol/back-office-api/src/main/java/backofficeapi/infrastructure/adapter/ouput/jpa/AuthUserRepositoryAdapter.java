package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.AuthUserRepositoryPort;
import backofficeapi.domain.model.AuthUserModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthUser;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.AuthUserMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.AuthUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
@Service
public class AuthUserRepositoryAdapter  implements AuthUserRepositoryPort {

    private AuthUserRepository authUserRepository;
    private AuthUserMapper authUserMapper;

    public AuthUserRepositoryAdapter(AuthUserRepository authUserRepository, AuthUserMapper authUserMapper) {
        this.authUserRepository = authUserRepository;
        this.authUserMapper = authUserMapper;
    }

    @Override
    public AuthUserModel save(AuthUserModel authUserModel) {
        AuthUser authUser = authUserMapper.toEntity(authUserModel);
        AuthUser savedAuthUser = authUserRepository.save(authUser);
        return authUserMapper.toModel(savedAuthUser);
    }

    @Override
    public Optional<AuthUserModel> findByEntraId(String entraId) {
        return authUserRepository
                .findByEntraId(entraId).map(authUserMapper::toModel);
    }

    @Override
    public Optional<AuthUserModel> findById(Long id) {
        return authUserRepository.findById(id).map(authUserMapper::toModel);
    }

    @Override
    public List<AuthUserModel> findAll() {
        return authUserRepository.findAllUser().stream()
                .map(authUserMapper::toModel)
                .toList();
    }

    @Override
    public boolean existsByEntraId(String entraId) {
        return authUserRepository.existsByEntraId(entraId);
    }
}
