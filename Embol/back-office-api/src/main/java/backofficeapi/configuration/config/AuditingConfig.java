package backofficeapi.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuditingConfig
 *   Descripción: Configuración de auditoría JPA para captura automática del usuario autenticado (Entra ID)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()
                    || "anonymousUser".equalsIgnoreCase(authentication.getName())) {
                return Optional.of("SYSTEM");
            }

            Object principal = authentication.getPrincipal();

            if (principal instanceof Jwt jwt) {
                String preferredUsername = jwt.getClaimAsString("preferred_username");
                if (preferredUsername != null && !preferredUsername.isBlank()) {
                    return Optional.of(preferredUsername);
                }
                String email = jwt.getClaimAsString("email");
                if (email != null && !email.isBlank()) {
                    return Optional.of(email);
                }
                String upn = jwt.getClaimAsString("upn");
                if (upn != null && !upn.isBlank()) {
                    return Optional.of(upn);
                }
                String name = jwt.getClaimAsString("name");
                if (name != null && !name.isBlank()) {
                    return Optional.of(name);
                }
            } else if (principal instanceof OidcUser oidcUser) {
                if (oidcUser.getPreferredUsername() != null && !oidcUser.getPreferredUsername().isBlank()) {
                    return Optional.of(oidcUser.getPreferredUsername());
                }
                if (oidcUser.getEmail() != null && !oidcUser.getEmail().isBlank()) {
                    return Optional.of(oidcUser.getEmail());
                }
            }

            return Optional.ofNullable(authentication.getName()).filter(s -> !s.isBlank()).or(() -> Optional.of("SYSTEM"));
        };
    }
}
