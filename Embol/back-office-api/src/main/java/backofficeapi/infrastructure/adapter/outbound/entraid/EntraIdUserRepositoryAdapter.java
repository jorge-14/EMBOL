package backofficeapi.infrastructure.adapter.outbound.entraid;

import backofficeapi.application.port.output.EntraIdUserRepositoryPort;
import backofficeapi.domain.model.TblUserModel;
import com.microsoft.graph.models.PasswordProfile;
import com.microsoft.graph.models.User;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import backofficeapi.domain.exception.EntraIdNotFoundException;
import backofficeapi.infrastructure.exception.EntraIdIntegrationException;

@Component
public class EntraIdUserRepositoryAdapter implements EntraIdUserRepositoryPort {

    private final GraphServiceClient graphClient;

    public EntraIdUserRepositoryAdapter(GraphServiceClient graphClient) {
        this.graphClient = graphClient;
    }

    @Override
    public void validateUser(String email) {
        try {
            var usersResponse = graphClient.users().get(requestConfiguration -> {
                requestConfiguration.queryParameters.filter = "userPrincipalName eq '" + email + "' or mail eq '" + email + "'";
            });
            if (usersResponse == null || usersResponse.getValue() == null || usersResponse.getValue().isEmpty()) {
                throw new EntraIdNotFoundException("El usuario " + email + " no existe en Microsoft Entra ID. Por favor solicite a TI que lo cree primero.");
            }
        } catch (EntraIdNotFoundException e) {
            throw e;
        } catch (Exception e) {
            // Error connecting to Graph API or invalid filter
            throw new EntraIdIntegrationException("Error de conexión al validar usuario en Microsoft Entra ID", e);
        }
    }

}
