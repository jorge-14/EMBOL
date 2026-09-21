package backofficeapi.infrastructure.adapter.outbound.entraid;

import backofficeapi.application.port.output.EntraIdRoleGroupPort;
import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.domain.model.TblGroupModel;
import com.microsoft.graph.models.AppRole;
import com.microsoft.graph.models.Group;
import com.microsoft.graph.models.Application;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import backofficeapi.domain.exception.EntraIdNotFoundException;
import backofficeapi.infrastructure.exception.EntraIdIntegrationException;

@Component
public class EntraIdRoleGroupAdapter implements EntraIdRoleGroupPort {

    private final GraphServiceClient graphClient;

    @Value("${entra.client-id}")
    private String clientId;

    public EntraIdRoleGroupAdapter(GraphServiceClient graphClient) {
        this.graphClient = graphClient;
    }

    @Override
    public void validateRole(String roleName) {
        try {
            var applications = graphClient.applications().get(requestConfiguration -> {
                requestConfiguration.queryParameters.filter = "appId eq '" + clientId + "'";
            });
            if (applications != null && applications.getValue() != null && !applications.getValue().isEmpty()) {
                Application app = applications.getValue().get(0);
                List<AppRole> appRoles = app.getAppRoles();
                if (appRoles != null) {
                    boolean exists = appRoles.stream().anyMatch(role -> 
                        (role.getDisplayName() != null && role.getDisplayName().equalsIgnoreCase(roleName)) || 
                        (role.getValue() != null && role.getValue().equalsIgnoreCase(roleName))
                    );
                    if (exists) return;
                }
            }
            throw new EntraIdNotFoundException("El App Role '" + roleName + "' no existe en la aplicación de Microsoft Entra ID. Solicite a TI que lo registre primero.");
        } catch (EntraIdNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EntraIdIntegrationException("Error de conexión al validar App Role en Microsoft Entra ID", e);
        }
    }

    @Override
    public void validateGroup(String groupName) {
        try {
            var groupsResponse = graphClient.groups().get(requestConfiguration -> {
                requestConfiguration.queryParameters.filter = "displayName eq '" + groupName + "'";
            });
            if (groupsResponse == null || groupsResponse.getValue() == null || groupsResponse.getValue().isEmpty()) {
                throw new EntraIdNotFoundException("El Security Group '" + groupName + "' no existe en Microsoft Entra ID. Solicite a TI que lo cree primero.");
            }
        } catch (EntraIdNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new EntraIdIntegrationException("Error de conexión al validar Security Group en Microsoft Entra ID", e);
        }
    }

}
