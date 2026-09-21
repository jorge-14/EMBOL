package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.group.CrudTblGroupUseCase;
import backofficeapi.application.port.input.group.FindGroupByIdUseCase;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;
import backofficeapi.domain.model.TblGroupModel;
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

@RestController
@RequestMapping("/api/v1/group")
public class TblGroupController {


    private final TblGroupRestMapper tblGroupRestMapper;
    private final CrudTblGroupUseCase crudTblGroupUseCase;
    private final FindGroupByIdUseCase findGroupByIdUseCase;

    public TblGroupController(CrudTblGroupUseCase crudTblGroupUseCase, TblGroupRestMapper tblGroupRestMapper,
                              FindGroupByIdUseCase findGroupByIdUseCase) {
        this.crudTblGroupUseCase = crudTblGroupUseCase;
        this.tblGroupRestMapper = tblGroupRestMapper;
        this.findGroupByIdUseCase = findGroupByIdUseCase;
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
        ResponsePage<TblGroupResponseDto> response = ResponsePage.from(pageResult, tblGroupRestMapper::toResponse);
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
}
