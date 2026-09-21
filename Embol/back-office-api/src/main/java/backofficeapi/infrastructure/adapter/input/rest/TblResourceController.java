package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.resource.GetResourceAccessUseCase;
import backofficeapi.application.port.input.resource.SaveResourceAccessUseCase;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;
import backofficeapi.infrastructure.adapter.input.rest.request.tblResource.SaveResourceAccessRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblResource.ResourceAccessResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblResourceController
 *   Descripción: Controlador REST para gestión de Recursos y Matriz de Accesos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial con documentación OpenAPI
 *----------------------------------------
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/tbl-resource")
@RequiredArgsConstructor
@Tag(name = "Recursos y Accesos", description = "Servicios para la consulta y configuración de menús, recursos y matriz de permisos por Rol o Grupo")
public class TblResourceController {

    private final GetResourceAccessUseCase getResourceAccessUseCase;
    private final SaveResourceAccessUseCase saveResourceAccessUseCase;

    @GetMapping("/access")
    @Operation(
            summary = "Consultar matriz de accesos",
            description = "Devuelve el árbol jerárquico de recursos padres (módulos) y recursos hijos (pantallas) con el estado de cada acción (VER, CREAR, MODIFICAR, ELIMINAR) para un Rol o Grupo específico.",
            tags = {"Recursos y Accesos"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Matriz de accesos obtenida correctamente"),
                    @ApiResponse(responseCode = "400", description = "Parámetros de solicitud inválidos"),
                    @ApiResponse(responseCode = "404", description = "Rol o Grupo no encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    public ResponseEntity<ResponseBody<ResourceAccessResponseDto>> getResourceAccess(
            @Parameter(description = "Modo de consulta: 'rol' o 'grupo'", example = "rol")
            @RequestParam(name = "mode", defaultValue = "rol") String mode,
            @Parameter(description = "Identificador del Rol o Grupo", example = "1", required = true)
            @RequestParam(name = "id") Long id) {

        log.info("[TblResourceController] Consultando matriz de accesos - Modo: {}, ID: {}", mode, id);
        ResourceAccessResponseDto responseDto = getResourceAccessUseCase.getAccess(mode, id);

        return ResponseEntity.ok(ResponseBody.success("Accesos obtenidos correctamente", responseDto));
    }

    @PostMapping("/access/save")
    @Operation(
            summary = "Guardar asignación de matriz de accesos",
            description = "Guarda y actualiza los permisos de recursos asignados a un Rol o Grupo específico.",
            tags = {"Recursos y Accesos"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Accesos guardados exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
                    @ApiResponse(responseCode = "404", description = "Rol o Grupo no encontrado"),
                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
            }
    )
    public ResponseEntity<ResponseBody<String>> saveResourceAccess(
            @Valid @RequestBody SaveResourceAccessRequestDto request) {

        log.info("[TblResourceController] Guardando matriz de accesos - Modo: {}, ID: {}",
                request.getMode(), request.getId());

        saveResourceAccessUseCase.saveAccess(request);

        return ResponseEntity.ok(ResponseBody.success("Accesos guardados exitosamente", null));
    }
}
