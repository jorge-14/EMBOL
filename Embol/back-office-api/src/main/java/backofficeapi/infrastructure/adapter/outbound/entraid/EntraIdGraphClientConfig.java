package backofficeapi.infrastructure.adapter.outbound.entraid;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntraIdGraphClientConfig {

    @Value("${entra.tenant-id}")
    private String tenantId;

    @Value("${entra.client-id}")
    private String clientId;

    @Value("${entra.client-secret}")
    private String clientSecret;

    @Bean
    public GraphServiceClient graphServiceClient() {
        ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .tenantId(tenantId)
                .clientSecret(clientSecret)
                .build();

        return new GraphServiceClient(credential, new String[]{"https://graph.microsoft.com/.default"});
    }
}
