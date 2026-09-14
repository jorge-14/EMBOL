package backofficeapi.application.port.input;

import backofficeapi.domain.model.AuthUserModel;

import java.util.List;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
public interface CrudAuthUserUseCase {

    AuthUserModel create(AuthUserModel authUserModel);

    AuthUserModel update(AuthUserModel authUserModel);

    AuthUserModel findByEntraId(String entraId);

    AuthUserModel findById(Long id);

    List<AuthUserModel> findAll();

    void delete(String entraId);

    AuthUserModel activate(String entraId);

    AuthUserModel deactivate(String entraId);
}
