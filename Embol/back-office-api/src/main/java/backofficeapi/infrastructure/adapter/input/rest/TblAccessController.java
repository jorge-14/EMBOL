package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.access.GetResourceAccessUseCase;
import backofficeapi.application.port.input.access.SaveResourceAccessUseCase;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;
import backofficeapi.infrastructure.adapter.input.rest.request.tblAccess.SaveResourceAccessRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblAccess.ResourceAccessResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblAccessController
 *   Descripción: Controlador REST para consulta y asignación de accesos a recursos y acciones
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial simplificada
 *----------------------------------------
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/tbl-access")
@RequiredArgsConstructor
public class TblAccessController {

    private final GetResourceAccessUseCase getResourceAccessUseCase;
    private final SaveResourceAccessUseCase saveResourceAccessUseCase;

    @GetMapping
    public ResponseEntity<ResponseBody<ResourceAccessResponseDto>> getResourceAccess(
            @RequestParam(name = "mode", defaultValue = "rol") String mode,
            @RequestParam(name = "id") Long id) {

        log.info("[TblAccessController] Consultando accesos - Modo: {}, ID: {}", mode, id);
        ResourceAccessResponseDto responseDto = getResourceAccessUseCase.getAccess(mode, id);

        return ResponseEntity.ok(ResponseBody.success("Accesos obtenidos correctamente", responseDto));
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseBody<String>> saveResourceAccess(
            @Valid @RequestBody SaveResourceAccessRequestDto request) {

        log.info("[TblAccessController] Guardando accesos - Modo: {}, ID: {}",
                request.getMode(), request.getId());

        saveResourceAccessUseCase.saveAccess(request);

        return ResponseEntity.ok(ResponseBody.success("Accesos guardados exitosamente", null));
    }
}
