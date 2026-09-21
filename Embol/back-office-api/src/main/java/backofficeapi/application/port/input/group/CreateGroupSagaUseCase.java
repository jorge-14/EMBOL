package backofficeapi.application.port.input.group;

import backofficeapi.domain.model.TblGroupModel;
import java.util.Map;

public interface CreateGroupSagaUseCase {
    
    /**
     * Executes the Saga to validate a group in Entra ID and create it locally.
     * @param group The group data.
     * @return The created TblGroupModel.
     */
    TblGroupModel createGroup(TblGroupModel group);
}
