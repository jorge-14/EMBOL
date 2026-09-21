package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.role.CrudTblRoleUseCase;
import backofficeapi.domain.model.TblRoleModel;
import backofficeapi.application.port.input.role.ListRoleUseCase;
import backofficeapi.application.port.input.role.GetRoleByIdUseCase;
import backofficeapi.application.port.input.role.CreateRoleSagaUseCase;
import backofficeapi.infrastructure.adapter.input.rest.request.saga.CreateRoleSagaRequestDto;
import backofficeapi.domain.enums.RoleStatus;
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

import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;
import org.springframework.http.HttpStatus;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import backofficeapi.domain.exception.EntraIdNotFoundException;
import backofficeapi.infrastructure.exception.EntraIdIntegrationException;

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
    private final CreateRoleSagaUseCase createRoleSagaUseCase;

    public TblRoleController(CrudTblRoleUseCase crudTblRoleUseCase, TblRoleRestMapper tblRoleRestMapper,
            ListRoleUseCase listRoleUseCase, GetRoleByIdUseCase getRoleByIdUseCase,
            CreateRoleSagaUseCase createRoleSagaUseCase) {
        this.crudTblRoleUseCase = crudTblRoleUseCase;
        this.tblRoleRestMapper = tblRoleRestMapper;
        this.listRoleUseCase = listRoleUseCase;
        this.getRoleByIdUseCase = getRoleByIdUseCase;
        this.createRoleSagaUseCase = createRoleSagaUseCase;
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
        ResponsePage<PageListRolResponseDto> response = tblRoleRestMapper.toResponsePage(pageResult);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-role/{id}")
    public ResponseEntity<ResponseBody<TblRoleResponseDto>> getRoleById(@PathVariable Long id,
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

    @PostMapping("/saga")
    @Operation(
        summary = "Crear rol con validación en Entra ID",
        description = "Valida si el App Role existe en Entra ID. Si existe, lo crea localmente. Si no existe, retorna error.",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Rol validado y creado con éxito en BD local.",
            content = @Content(mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error de validación o datos faltantes",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno - La SAGA falló.",
            content = @Content
        )
    })
    public ResponseEntity<ResponseBody<TblRoleResponseDto>> createRoleSaga(
        @Parameter(description = "Objeto con los datos del nuevo rol a crear", required = true)
        @RequestBody CreateRoleSagaRequestDto roleDto
    ) {
        log.info("Iniciando validación de SAGA para el rol: {}", roleDto.getName());
        try {
            TblRoleModel role = new TblRoleModel();
            role.setSNombre(roleDto.getName());
            role.setSDescripcion(roleDto.getDescription());
            role.setSEstado(RoleStatus.ACTIVE);
            role.setSRolBase(false);

            TblRoleModel result = createRoleSagaUseCase.createRole(role);
            log.info("Rol creado exitosamente mediante SAGA: {}", roleDto.getName());
            return ResponseEntity.ok(ResponseBody.success("Rol validado y creado con éxito", tblRoleRestMapper.toResponseDto(result)));
        } catch (EntraIdNotFoundException e) {
            log.warn("Fallo de validación de negocio para rol {}: {}", roleDto.getName(), e.getMessage());
            return ResponseEntity.badRequest().body(ResponseBody.<TblRoleResponseDto>error(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage()));
        } catch (EntraIdIntegrationException e) {
            log.error("Fallo de infraestructura/conexión con Entra ID para rol {}: {}", roleDto.getName(), e.getMessage(), e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(ResponseBody.<TblRoleResponseDto>error(String.valueOf(HttpStatus.BAD_GATEWAY.value()), "Ocurrió un problema de conexión con Microsoft Entra ID. Intente más tarde."));
        } catch (Exception e) {
            log.error("Error técnico creando rol con SAGA {}: {}", roleDto.getName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseBody.<TblRoleResponseDto>error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "Ocurrió un problema al procesar la solicitud."));
        }
    }
}
