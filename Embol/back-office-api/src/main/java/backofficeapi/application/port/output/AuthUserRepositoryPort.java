package backofficeapi.application.port.output;

import backofficeapi.domain.model.AuthUserModel;

import java.util.List;
import java.util.Optional;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
public interface AuthUserRepositoryPort {
    AuthUserModel save(AuthUserModel authUserModel);

    Optional<AuthUserModel> findByEntraId(String entraId);

    Optional<AuthUserModel> findById(Long id);

    List<AuthUserModel> findAll();

    boolean existsByEntraId(String entraId);
}
