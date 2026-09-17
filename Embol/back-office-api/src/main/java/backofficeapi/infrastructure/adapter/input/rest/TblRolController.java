package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.rol.CrudTblRolUseCase;
import backofficeapi.application.port.input.rol.ListRoleUseCase;
import backofficeapi.domain.model.TblRolModel;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponsePage;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblRolRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.tblRol.TblRolRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.tblRol.TblRolUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.PageListRolResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.TblRolResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.ListRoleShortResponse;
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
    public ResponseEntity<TblRolResponseDto> createRole(@Valid @RequestBody TblRolRequestDto tblRolRequestDto,
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
        TblRolResponseDto tblRolResponseDto = tblRolRestMapper.toAuthRoleResponseDto(tblRolModelCreate);
        return ResponseEntity.ok(tblRolResponseDto);
    }

    @PutMapping("/update-role/{id}")
    public ResponseEntity<TblRolResponseDto> updateRole(@PathVariable Long id, @Valid @RequestBody TblRolUpdateRequestDto tblRolUpdateRequestDto,
                                                        Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken jwtAuth) {

            Jwt jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("preferred_username");
            log.info("Usuario autenticado: {} | Actualizando rol con ID: {}", username, id);
        } else {
            log.info("Actualizando rol con ID: {}", id);
        }

        TblRolModel tblRolModel = tblRolRestMapper.toModelUpdate(tblRolUpdateRequestDto);
        TblRolModel updatedModel = crudTblRolUseCase.updateRole(id, tblRolModel);
        TblRolResponseDto tblRolResponseDto = tblRolRestMapper.toAuthRoleResponseDto(updatedModel);
        return ResponseEntity.ok(tblRolResponseDto);
    }

    @DeleteMapping("/delete-role/{id}")
    public ResponseEntity<TblRolResponseDto> deleteRole(@PathVariable Long id,
                                                        Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken jwtAuth) {

            Jwt jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("preferred_username");
            log.info("Usuario autenticado: {} | Eliminando rol con ID: {}", username, id);
        } else {
            log.info("Eliminando rol con ID: {}", id);
        }

        TblRolModel deletedModel = crudTblRolUseCase.deleteRole(id);
        TblRolResponseDto tblRolResponseDto = tblRolRestMapper.toAuthRoleResponseDto(deletedModel);
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
        Page<TblRolModel> pageResult = crudTblRolUseCase.pageListRol(pageable);
        ResponsePage<PageListRolResponseDto> response = ResponsePage.from(pageResult, tblRolRestMapper::toPageResponse);
        return ResponseEntity.ok(response);
    }
}
