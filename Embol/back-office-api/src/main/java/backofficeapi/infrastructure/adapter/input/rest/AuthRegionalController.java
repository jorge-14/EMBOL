package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.CrudAuthRegionalUseCase;
import backofficeapi.domain.model.AuthRegionalModel;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;
import backofficeapi.infrastructure.adapter.input.rest.mapper.AuthRegionalRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthRegionalCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthRegionalUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthRegionalPageResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthRegionalResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthRegionalController
 *   Descripción: Controlador REST para operaciones CRUD de AuthRegional (auth_regional)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/auth-regional")
@RequiredArgsConstructor
public class AuthRegionalController {

    private final CrudAuthRegionalUseCase crudAuthRegionalUseCase;
    private final AuthRegionalRestMapper mapper;

    @PostMapping("/create-regional")
    public ResponseEntity<ResponseBody<AuthRegionalResponseDto>> create(
            @Valid @RequestBody AuthRegionalCreateRequestDto request) {
        log.info("Iniciando creación de regional con código: {}", request.getCode());
        AuthRegionalModel model = mapper.toModel(request);
        AuthRegionalModel created = crudAuthRegionalUseCase.createRegional(model);
        AuthRegionalResponseDto response = mapper.toResponse(created);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBody.success("Regional creada exitosamente", response));
    }

    @GetMapping("/get-all-regionals")
    public ResponseEntity<ResponseBody<List<AuthRegionalResponseDto>>> listAll() {
        log.info("Consultando listado general de regionales");
        List<AuthRegionalResponseDto> response = crudAuthRegionalUseCase.listAllRegionals().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(ResponseBody.success("Regionales listadas exitosamente", response));
    }

    @GetMapping("/get-regional/{id}")
    public ResponseEntity<ResponseBody<AuthRegionalResponseDto>> getById(@PathVariable Long id) {
        log.info("Consultando regional con ID: {}", id);
        return crudAuthRegionalUseCase.getRegionalById(id)
                .map(mapper::toResponse)
                .map(res -> ResponseEntity.ok(ResponseBody.success("Regional encontrada exitosamente", res)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBody.error("404", "No se encontró la regional con ID: " + id)));
    }

    @PutMapping("/update-regional/{id}")
    public ResponseEntity<ResponseBody<AuthRegionalResponseDto>> update(@PathVariable Long id,
            @Valid @RequestBody AuthRegionalUpdateRequestDto request) {
        log.info("Actualizando regional con ID: {}", id);
        AuthRegionalModel model = mapper.toModel(request);
        AuthRegionalModel updated = crudAuthRegionalUseCase.updateRegional(id, model);
        AuthRegionalResponseDto response = mapper.toResponse(updated);
        return ResponseEntity.ok(ResponseBody.success("Regional actualizada exitosamente", response));
    }

    @PutMapping("/delete-regional/{id}")
    public ResponseEntity<ResponseBody<Void>> delete(@PathVariable Long id) {
        log.info("Eliminando regional con ID: {}", id);
        crudAuthRegionalUseCase.deleteRegionalById(id);
        return ResponseEntity.ok(ResponseBody.success("Regional eliminada exitosamente", null));
    }

    @GetMapping("/get-paginated-regionals")
    public ResponseEntity<ResponseBody<AuthRegionalPageResponseDto>> listPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdDate") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "DESC") Sort.Direction sortDir) {
        log.info("Consultando página de regionales: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy,
                sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));
        Page<AuthRegionalModel> pageResult = crudAuthRegionalUseCase.getListPageRegional(pageable);
        AuthRegionalPageResponseDto response = mapper.toPageResponse(pageResult);
        return ResponseEntity.ok(ResponseBody.success("Regionales paginadas exitosamente", response));
    }
}
