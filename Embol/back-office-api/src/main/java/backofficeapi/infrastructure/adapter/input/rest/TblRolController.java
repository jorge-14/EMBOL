package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.CrudTblRolUseCase;
import backofficeapi.application.port.input.ListRoleUseCase;
import backofficeapi.domain.model.TblRolModel;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblRolRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.TblRolRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthRoleResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.ListRoleShortResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
@RequestMapping("/api/v1/tbl-role")
public class TblRolController {

    private final CrudTblRolUseCase crudTblRolUseCase;
    private final TblRolRestMapper tblRolRestMapper;
    private final ListRoleUseCase listRoleUseCase;

    public TblRolController(CrudTblRolUseCase crudTblRolUseCase, TblRolRestMapper tblRolRestMapper, ListRoleUseCase listRoleUseCase) {
        this.crudTblRolUseCase = crudTblRolUseCase;
        this.tblRolRestMapper = tblRolRestMapper;
        this.listRoleUseCase = listRoleUseCase;
    }

    @GetMapping("/list-role-short")
    public ResponseEntity<List<ListRoleShortResponse>> listRoleShort() {
        List<ListRoleShortResponse> response = listRoleUseCase.listRoleShort().stream()
                .map(tblRolRestMapper::toListRoleShortResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-role")
    public ResponseEntity<AuthRoleResponseDto> createRole(
            @RequestBody TblRolRequestDto tblRolRequestDto,
            Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken jwtAuth) {

            Jwt jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("preferred_username");
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
