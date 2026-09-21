package backofficeapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/error", "/oauth2/**", "/login/**", "/api/public/**", "/swagger-ui/**",
                                "/v3/api-docs/**", "/api/v1/**")
                        .permitAll()
                        .anyRequest().authenticated())
                // Habilita el login en el navegador (Redirige a Microsoft automáticamente)
                .oauth2Login(org.springframework.security.config.Customizer.withDefaults())
                // Mantiene el Resource Server para poder seguir recibiendo tokens de
                // aplicaciones externas
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    // Este método extrae con prioridad: si tiene roles usa los roles; si no, toma
    // los grupos como respaldo
    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            List<String> roles = jwt.getClaimAsStringList("roles");
            List<String> groups = jwt.getClaimAsStringList("groups");

            // Si vienen roles en el token de Entra ID
            if (roles != null && !roles.isEmpty()) {
                for (String role : roles) {
                    // Se asegura el prefijo ROLE_ estándar en Spring Security
                    String roleAuthority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                    authorities.add(new SimpleGrantedAuthority(roleAuthority));
                }
            }
            // Si NO tiene roles, se cargan sus grupos
            else if (groups != null && !groups.isEmpty()) {
                for (String group : groups) {
                    authorities.add(new SimpleGrantedAuthority("GROUP_" + group));
                }
            }

            return authorities;
        });
        return converter;
    }
}
