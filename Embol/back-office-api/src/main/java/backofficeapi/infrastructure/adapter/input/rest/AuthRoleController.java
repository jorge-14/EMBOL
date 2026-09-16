package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.CrudAuthRoleUseCase;
import backofficeapi.domain.model.AuthRoleModel;
import backofficeapi.infrastructure.adapter.input.rest.mapper.AuthRoleRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthRoleRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthRoleResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */


@Slf4j
@RestController
@RequestMapping("/api/v1/auth-role")
public class AuthRoleController {

    private final CrudAuthRoleUseCase crudAuthRoleUseCase;
    private final AuthRoleRestMapper authRoleRestMapper;

    public AuthRoleController(CrudAuthRoleUseCase crudAuthRoleUseCase, AuthRoleRestMapper authRoleRestMapper) {
        this.crudAuthRoleUseCase = crudAuthRoleUseCase;
        this.authRoleRestMapper = authRoleRestMapper;
    }

    @PostMapping("/create-role")
    public ResponseEntity<AuthRoleResponseDto> createRole(
            @RequestBody AuthRoleRequestDto authRoleRequestDto,
            Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken jwtAuth) {

            Jwt jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("preferred_username"); // o "sub", "name", según tu IdP
            log.info("Usuario autenticado: {} | Autenticado: {}", username, authentication.isAuthenticated());
        } else {
            log.warn("Petición sin autenticación válida");
        }

        AuthRoleModel authRoleModel = authRoleRestMapper.toModel(authRoleRequestDto);
        AuthRoleModel authRoleModelCreate = crudAuthRoleUseCase.createRole(authRoleModel);
        AuthRoleResponseDto authRoleResponseDto = authRoleRestMapper.toAuthRoleResponseDto(authRoleModelCreate);
        return ResponseEntity.ok(authRoleResponseDto);
    }
}
