package backofficeapi.application.port.input.role;

import backofficeapi.domain.model.TblRoleModel;
import java.util.Map;

public interface CreateRoleSagaUseCase {
    
    /**
     * Executes the Saga to validate a role in Entra ID and create it locally.
     * @param role The role data.
     * @return The created TblRoleModel.
     */
    TblRoleModel createRole(TblRoleModel role);
}
