package backofficeapi.application.usecase;

import backofficeapi.application.port.input.group.CrudTblGroupUseCase;
import backofficeapi.application.port.input.role.CrudTblRoleUseCase;
import backofficeapi.application.port.output.EntraIdRoleGroupPort;
import backofficeapi.domain.model.TblGroupModel;
import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.application.port.input.group.CreateGroupSagaUseCase;
import backofficeapi.application.port.input.role.CreateRoleSagaUseCase;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoleGroupSagaOrchestrator implements CreateRoleSagaUseCase, CreateGroupSagaUseCase {

    private final CrudTblRoleUseCase localRoleService;
    private final CrudTblGroupUseCase localGroupService;
    private final EntraIdRoleGroupPort entraIdRoleGroupPort;

    public RoleGroupSagaOrchestrator(CrudTblRoleUseCase localRoleService,
                                     CrudTblGroupUseCase localGroupService,
                                     EntraIdRoleGroupPort entraIdRoleGroupPort) {
        this.localRoleService = localRoleService;
        this.localGroupService = localGroupService;
        this.entraIdRoleGroupPort = entraIdRoleGroupPort;
    }

    @Override
    public TblRoleModel createRole(TblRoleModel role) {
        // Step 1: Validate in Entra ID (Throws exception if not found or error)
        entraIdRoleGroupPort.validateRole(role.getSNombre());

        // Step 2: Save local
        return localRoleService.createRole(role);
    }

    @Override
    public TblGroupModel createGroup(TblGroupModel group) {
        // Step 1: Validate in Entra ID (Throws exception if not found or error)
        entraIdRoleGroupPort.validateGroup(group.getSNombre());

        // Step 2: Save local
        return localGroupService.createGroup(group);
    }
}
