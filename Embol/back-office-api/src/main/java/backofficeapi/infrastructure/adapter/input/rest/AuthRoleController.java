package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.CrudTblRolUseCase;
import backofficeapi.domain.model.TblRolModel;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblRolRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.TblRolRequestDto;
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

    private final CrudTblRolUseCase crudTblRolUseCase;
    private final TblRolRestMapper tblRolRestMapper;

    public AuthRoleController(CrudTblRolUseCase crudTblRolUseCase, TblRolRestMapper tblRolRestMapper) {
        this.crudTblRolUseCase = crudTblRolUseCase;
        this.tblRolRestMapper = tblRolRestMapper;
    }

    @PostMapping("/create-role")
    public ResponseEntity<AuthRoleResponseDto> createRole(
            @RequestBody TblRolRequestDto tblRolRequestDto,
            Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken jwtAuth) {

            Jwt jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("preferred_username"); // o "sub", "name", según tu IdP
            log.info("Usuario autenticado: {} | Autenticado: {}", username, authentication.isAuthenticated());
        } else {
            log.warn("Petición sin autenticación válida");
        }

        TblRolModel tblRolModel = tblRolRestMapper.toModel(tblRolRequestDto);
        TblRolModel tblRolModelCreate = crudTblRolUseCase.createRole(tblRolModel);
        AuthRoleResponseDto authRoleResponseDto = tblRolRestMapper.toAuthRoleResponseDto(tblRolModelCreate);
        return ResponseEntity.ok(authRoleResponseDto);
    }
}
