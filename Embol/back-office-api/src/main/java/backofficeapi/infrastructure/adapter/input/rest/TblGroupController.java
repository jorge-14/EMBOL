package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.group.CrudTblGroupUseCase;
import backofficeapi.application.port.input.group.FindGroupByIdUseCase;
import backofficeapi.application.port.input.group.CreateGroupSagaUseCase;
import backofficeapi.infrastructure.adapter.input.rest.request.saga.CreateGroupSagaRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponsePage;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblGroupRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.tblGroup.TblGroupRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblGroup.TblGroupResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;
import backofficeapi.domain.model.TblGroupModel;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

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
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/group")
public class TblGroupController {


    private final TblGroupRestMapper tblGroupRestMapper;
    private final CrudTblGroupUseCase crudTblGroupUseCase;
    private final FindGroupByIdUseCase findGroupByIdUseCase;
    private final CreateGroupSagaUseCase createGroupSagaUseCase;

    public TblGroupController(CrudTblGroupUseCase crudTblGroupUseCase, TblGroupRestMapper tblGroupRestMapper,
                              FindGroupByIdUseCase findGroupByIdUseCase,
                              CreateGroupSagaUseCase createGroupSagaUseCase) {
        this.crudTblGroupUseCase = crudTblGroupUseCase;
        this.tblGroupRestMapper = tblGroupRestMapper;
        this.findGroupByIdUseCase = findGroupByIdUseCase;
        this.createGroupSagaUseCase = createGroupSagaUseCase;
    }

    @PostMapping("/create-group")
    @Operation(summary = "Crear nuevo grupo", description = "Registra un nuevo grupo en el sistema.", tags = {
            "Grupos" }, responses = {
            @ApiResponse(responseCode = "200", description = "Grupo creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TblGroupResponseDto> create(@Valid @RequestBody TblGroupRequestDto tblGroupRequestDto) {

        TblGroupModel model = tblGroupRestMapper.toModel(tblGroupRequestDto);
        TblGroupModel created = crudTblGroupUseCase.createGroup(model);
        TblGroupResponseDto response  = tblGroupRestMapper.toResponse(created);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated-group")
    @Operation(summary = "Listar grupos paginados", description = "Obtiene una página de grupos con ordenamiento y paginación configurable.", tags = {
            "Grupos" }, responses = {
            @ApiResponse(responseCode = "200", description = "Página de grupos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponsePage<TblGroupResponseDto>> listPage(
            @Parameter(description = "Número de página (0-index)", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
            @Parameter(description = "Cantidad de registros por página", example = "20") @RequestParam(value = "size", defaultValue = "20") int size,
            @Parameter(description = "Campo de ordenamiento", example = "modifiedDate") @RequestParam(value = "sortBy", defaultValue = "modifiedDate") String sortBy,
            @Parameter(description = "Dirección de orden (ASC o DESC)", example = "DESC") @RequestParam(value = "sortDir", defaultValue = "DESC") Sort.Direction sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));
        Page<TblGroupModel> pageResult = crudTblGroupUseCase.pageListGroup(pageable);
        ResponsePage<TblGroupResponseDto> response = tblGroupRestMapper.toResponsePage(pageResult);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-group/{id}")
    @Operation(summary = "Actualizar grupo", description = "Actualiza los datos de un grupo existente.", tags = {
            "Grupos" }, responses = {
            @ApiResponse(responseCode = "200", description = "Grupo actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Grupo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<TblGroupResponseDto> updateGroup(
            @Parameter(description = "ID del grupo", example = "1", required = true) @PathVariable Long id,
            @Valid @RequestBody TblGroupRequestDto tblGroupRequestDto) {
        TblGroupModel model = tblGroupRestMapper.toModel(tblGroupRequestDto);
        TblGroupModel update = crudTblGroupUseCase.updateGroup(id, model);
        TblGroupResponseDto response  = tblGroupRestMapper.toResponse(update);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-group/{id}")
    @Operation(summary = "Eliminar grupo", description = "Elimina un grupo por su ID.", tags = {
            "Grupos" }, responses = {
            @ApiResponse(responseCode = "200", description = "Grupo eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Grupo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseBody<Boolean>> deleteGroup(
            @Parameter(description = "ID del grupo", example = "1", required = true) @PathVariable Long id) {
        Boolean delete = crudTblGroupUseCase.deleteGroup(id);
        return ResponseEntity.ok(ResponseBody.success("El grupo fue eliminado exitosamente", delete));
    }

    @GetMapping("/information-group-by-id/{id}")
    @Operation(summary = "Obtener información del grupo por ID", description = "Busca la información de un grupo específico por su identificador numérico.", tags = {
            "Grupos" }, responses = {
            @ApiResponse(responseCode = "200", description = "Información del grupo obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Grupo no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<ResponseBody<TblGroupResponseDto>> informationGroupById(
            @Parameter(description = "ID del grupo", example = "1", required = true) @PathVariable Long id) {
        TblGroupModel information =  findGroupByIdUseCase.getInformationGroupById(id);
        TblGroupResponseDto response  = tblGroupRestMapper.toResponse(information);
        return ResponseEntity.ok(ResponseBody.success("La informacion del grupo fue armada exitosamente", response));
    }

    @PostMapping("/saga")
    @Operation(
        summary = "Crear grupo con validación en Entra ID",
        description = "Valida si el grupo existe en Entra ID. Si existe, lo crea localmente. Si no existe, retorna error.",
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Grupo validado y creado con éxito en BD local.",
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
    public ResponseEntity<ResponseBody<TblGroupResponseDto>> createGroupSaga(
        @Parameter(description = "Objeto con los datos del nuevo grupo a crear", required = true)
        @RequestBody CreateGroupSagaRequestDto groupDto
    ) {
        log.info("Iniciando validación de SAGA para el grupo: {}", groupDto.getName());
        try {
            TblGroupModel group = new TblGroupModel();
            group.setSNombre(groupDto.getName());
            group.setSDescripcion(groupDto.getDescription());
            group.setDeleted(false);

            TblGroupModel result = createGroupSagaUseCase.createGroup(group);
            log.info("Grupo creado exitosamente mediante SAGA: {}", groupDto.getName());
            return ResponseEntity.ok(ResponseBody.success("Grupo validado y creado con éxito", tblGroupRestMapper.toResponse(result)));
        } catch (EntraIdNotFoundException e) {
            log.warn("Fallo de validación de negocio para grupo {}: {}", groupDto.getName(), e.getMessage());
            return ResponseEntity.badRequest().body(ResponseBody.<TblGroupResponseDto>error(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage()));
        } catch (EntraIdIntegrationException e) {
            log.error("Fallo de infraestructura/conexión con Entra ID para grupo {}: {}", groupDto.getName(), e.getMessage(), e.getCause());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(ResponseBody.<TblGroupResponseDto>error(String.valueOf(HttpStatus.BAD_GATEWAY.value()), "Ocurrió un problema de conexión con Microsoft Entra ID. Intente más tarde."));
        } catch (Exception e) {
            log.error("Error técnico creando grupo con SAGA {}: {}", groupDto.getName(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseBody.<TblGroupResponseDto>error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "Ocurrió un problema al procesar la solicitud."));
        }
    }
}
