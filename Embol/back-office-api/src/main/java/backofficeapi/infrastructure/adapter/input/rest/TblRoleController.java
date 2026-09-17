package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.role.CrudTblRoleUseCase;
import backofficeapi.application.port.input.role.ListRoleUseCase;
import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponsePage;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblRoleRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.tblRole.TblRoleRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.tblRole.TblRoleUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.ListRoleShortResponse;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.PageListRolResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.TblRoleResponseDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<TblRoleResponseDto> createRole(@Valid @RequestBody TblRoleRequestDto tblRoleRequestDto,
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
        TblRoleResponseDto authRoleResponseDto = tblRoleRestMapper.toAuthRoleResponseDto(tblRoleModelCreate);
        return ResponseEntity.ok(authRoleResponseDto);
    }

    @PutMapping("/update-role/{id}")
    public ResponseEntity<TblRoleResponseDto> updateRole(@PathVariable Long id, @Valid @RequestBody TblRoleUpdateRequestDto tblRolUpdateRequestDto,
                                                         Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken jwtAuth) {

            Jwt jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("preferred_username");
            log.info("Usuario autenticado: {} | Actualizando rol con ID: {}", username, id);
        } else {
            log.info("Actualizando rol con ID: {}", id);
        }

        TblRoleModel tblRolModel = tblRoleRestMapper.toModelUpdate(tblRolUpdateRequestDto);
        TblRoleModel updatedModel = crudTblRoleUseCase.updateRole(id, tblRolModel);
        TblRoleResponseDto tblRolResponseDto = tblRoleRestMapper.toAuthRoleResponseDto(updatedModel);
        return ResponseEntity.ok(tblRolResponseDto);
    }

    @DeleteMapping("/delete-role/{id}")
    public ResponseEntity<TblRoleResponseDto> deleteRole(@PathVariable Long id,
                                                        Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken jwtAuth) {

            Jwt jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("preferred_username");
            log.info("Usuario autenticado: {} | Eliminando rol con ID: {}", username, id);
        } else {
            log.info("Eliminando rol con ID: {}", id);
        }

        TblRoleModel deletedModel = crudTblRoleUseCase.deleteRole(id);
        TblRoleResponseDto tblRolResponseDto = tblRoleRestMapper.toAuthRoleResponseDto(deletedModel);
        return ResponseEntity.ok(tblRolResponseDto);
    }

    @GetMapping("page-rol")
    public ResponseEntity<ResponsePage<PageListRolResponseDto>> listPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sortBy", defaultValue = "modifiedDate") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "DESC") Sort.Direction sortDir
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));
        Page<TblRoleModel> pageResult = crudTblRoleUseCase.pageListRol(pageable);
        ResponsePage<PageListRolResponseDto> response = ResponsePage.from(pageResult, tblRoleRestMapper::toPageResponse);
        return ResponseEntity.ok(response);
    }
}
