package backofficeapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/error",
                                "/oauth2/**",
                                "/login/**",
                                "/h2-console/**"
                        ).permitAll()

                        .requestMatchers("/api/public/**").permitAll()

                        .anyRequest().authenticated()
                )

                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/api/hello", true)
                )

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                )

                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }
}
