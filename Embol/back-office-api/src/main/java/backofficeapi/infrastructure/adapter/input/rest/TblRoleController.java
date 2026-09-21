package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.role.CrudTblRoleUseCase;
import backofficeapi.application.port.input.role.GetRoleByIdUseCase;
import backofficeapi.application.port.input.role.ListRoleUseCase;
import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponsePage;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblRoleRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.tblRole.TblRoleRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.tblRole.TblRoleUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.ListRoleShortResponse;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.PageListRolResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblRole.TblRoleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;

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
    private final GetRoleByIdUseCase getRoleByIdUseCase;

    public TblRoleController(CrudTblRoleUseCase crudTblRoleUseCase, TblRoleRestMapper tblRoleRestMapper,
            ListRoleUseCase listRoleUseCase, GetRoleByIdUseCase getRoleByIdUseCase) {
        this.crudTblRoleUseCase = crudTblRoleUseCase;
        this.tblRoleRestMapper = tblRoleRestMapper;
        this.listRoleUseCase = listRoleUseCase;
        this.getRoleByIdUseCase = getRoleByIdUseCase;
    }

    @GetMapping("/list-role-short")
    @Operation(summary = "Listar roles resumidos", description = "Obtiene la lista resumida de roles activos.", tags = {
            "Roles" }, responses = {
            @ApiResponse(responseCode = "200", description = "Roles listados exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<ListRoleShortResponse>> listRoleShort() {
        List<ListRoleShortResponse> response = listRoleUseCase.listRoleShort().stream()
                .map(tblRoleRestMapper::toListRoleShortResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create-role")
    @Operation(summary = "Crear nuevo rol", description = "Registra un nuevo rol en el sistema.", tags = {
            "Roles" }, responses = {
            @ApiResponse(responseCode = "200", description = "Rol creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(summary = "Actualizar rol", description = "Actualiza los datos de un rol existente.", tags = {
            "Roles" }, responses = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TblRoleResponseDto> updateRole(
            @Parameter(description = "ID del rol", example = "1", required = true) @PathVariable Long id,
            @Valid @RequestBody TblRoleUpdateRequestDto tblRolUpdateRequestDto,
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
    @Operation(summary = "Eliminar rol", description = "Elimina un rol por su ID.", tags = {
            "Roles" }, responses = {
            @ApiResponse(responseCode = "200", description = "Rol eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TblRoleResponseDto> deleteRole(
            @Parameter(description = "ID del rol", example = "1", required = true) @PathVariable Long id,
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
    @Operation(summary = "Listar roles paginados", description = "Obtiene una página de roles con ordenamiento y paginación configurable.", tags = {
            "Roles" }, responses = {
            @ApiResponse(responseCode = "200", description = "Página de roles obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponsePage<PageListRolResponseDto>> listPage(
            @Parameter(description = "Número de página (0-index)", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Cantidad de registros por página", example = "20") @RequestParam(value = "size", defaultValue = "20") int size,
            @Parameter(description = "Campo de ordenamiento", example = "modifiedDate") @RequestParam(value = "sortBy", defaultValue = "modifiedDate") String sortBy,
            @Parameter(description = "Dirección de orden (ASC o DESC)", example = "DESC") @RequestParam(value = "sortDir", defaultValue = "DESC") Sort.Direction sortDir
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));
        Page<TblRoleModel> pageResult = crudTblRoleUseCase.pageListRol(pageable);
        ResponsePage<PageListRolResponseDto> response = ResponsePage.from(pageResult, tblRoleRestMapper::toPageResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-role/{id}")
    @Operation(summary = "Obtener rol por ID", description = "Busca un rol específico por su identificador numérico.", tags = {
            "Roles" }, responses = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseBody<TblRoleResponseDto>> getRoleById(
            @Parameter(description = "ID del rol", example = "1", required = true) @PathVariable Long id,
            Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            String username = jwt.getClaimAsString("preferred_username");
            log.info("Usuario autenticado: {} | Consultando rol con ID: {}", username, id);
        } else {
            log.info("Consultando rol con ID: {}", id);
        }

        TblRoleModel roleModel = getRoleByIdUseCase.getRoleById(id);
        TblRoleResponseDto responseDto = tblRoleRestMapper.toResponseDto(roleModel);
        return ResponseEntity.ok(ResponseBody.success("La informacion del grupo fue armada exitosamente", responseDto));
    }
}
