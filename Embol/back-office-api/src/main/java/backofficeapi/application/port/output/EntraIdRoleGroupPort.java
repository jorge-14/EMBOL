package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.domain.model.TblGroupModel;
import java.util.List;

public interface EntraIdRoleGroupPort {

    /**
     * Validates if an App Role exists in Microsoft Entra ID. Throws exceptions if not valid or error.
     * @param roleName The display name of the App Role.
     */
    void validateRole(String roleName);



    /**
     * Validates if a Security Group exists in Microsoft Entra ID. Throws exceptions if not valid or error.
     * @param groupName The display name of the Security Group.
     */
    void validateGroup(String groupName);




}
