package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.CrudTblRoleUseCase;
import backofficeapi.application.port.input.ListRoleUseCase;
import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblRoleRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.TblRoleRequestDto;
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
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRoleController
 *   Descripción: Controlador REST para operaciones CRUD de Role
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/tbl-role")
public class TblRoleController {

    private final CrudTblRoleUseCase crudTblRoleUseCase;
    private final TblRoleRestMapper tblRoleRestMapper;
    private final ListRoleUseCase listRoleUseCase;

    public TblRoleController(CrudTblRoleUseCase crudTblRoleUseCase, TblRoleRestMapper tblRoleRestMapper,
            ListRoleUseCase listRoleUseCase) {
        this.crudTblRoleUseCase = crudTblRoleUseCase;
        this.tblRoleRestMapper = tblRoleRestMapper;
        this.listRoleUseCase = listRoleUseCase;
    }

    @GetMapping("/list-role-short")
    public ResponseEntity<List<ListRoleShortResponse>> listRoleShort() {
        List<ListRoleShortResponse> response = listRoleUseCase.listRoleShort().stream()
                .map(tblRoleRestMapper::toListRoleShortResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-role")
    public ResponseEntity<AuthRoleResponseDto> createRole(
            @RequestBody TblRoleRequestDto tblRoleRequestDto,
            Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken jwtAuth) {

            Jwt jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("preferred_username");
            log.info("Usuario autenticado: {} | Autenticado: {}", username, authentication.isAuthenticated());
        } else {
            log.warn("Petición sin autenticación válida");
        }

        TblRoleModel tblRoleModel = tblRoleRestMapper.toModel(tblRoleRequestDto);
        TblRoleModel tblRoleModelCreate = crudTblRoleUseCase.createRole(tblRoleModel);
        AuthRoleResponseDto authRoleResponseDto = tblRoleRestMapper.toAuthRoleResponseDto(tblRoleModelCreate);
        return ResponseEntity.ok(authRoleResponseDto);
    }
}
