package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblUserModel;
import java.util.List;

public interface EntraIdUserRepositoryPort {
    
    /**
     * Validates if a user exists in Microsoft Entra ID. Throws exceptions if not valid or error.
     * @param email The user's UserPrincipalName or Email.
     */
    void validateUser(String email);
    

    

}
