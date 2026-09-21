package backofficeapi.application.port.input;

import backofficeapi.domain.model.TblUserModel;
import java.util.Map;

public interface CreateUserSagaUseCase {
    
    /**
     * Executes the Saga to validate a user in Entra ID and create it locally.
     * @param user The user data.
     * @return The created TblUserModel.
     */
    TblUserModel createUser(TblUserModel user);
}
