package backofficeapi.application.usecase;

import backofficeapi.application.port.input.CreateUserSagaUseCase;
import backofficeapi.application.port.input.user.CrudTblUserUseCase;
import backofficeapi.application.port.output.EntraIdUserRepositoryPort;
import backofficeapi.domain.model.TblUserModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserSagaOrchestrator implements CreateUserSagaUseCase {

    private final CrudTblUserUseCase localUserService;
    private final EntraIdUserRepositoryPort entraIdUserRepositoryPort;

    public UserSagaOrchestrator(CrudTblUserUseCase localUserService, EntraIdUserRepositoryPort entraIdUserRepositoryPort) {
        this.localUserService = localUserService;
        this.entraIdUserRepositoryPort = entraIdUserRepositoryPort;
    }

    @Override
    public TblUserModel createUser(TblUserModel user) {
        // Step 1: Validate in Entra ID (Throws exception if not found or error)
        entraIdUserRepositoryPort.validateUser(user.getEmail());

        // Step 2: Save local user
        return localUserService.createUser(user);
    }
}
