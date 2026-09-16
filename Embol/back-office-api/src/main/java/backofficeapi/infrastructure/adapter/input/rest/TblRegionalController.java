package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.CrudTblRegionalUseCase;
import backofficeapi.domain.model.TblRegionalModel;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblRegionalRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.TblRegionalCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.TblRegionalUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.TblRegionalPageResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.TblRegionalResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 *   Código de Objeto: TblRegionalController
 *   Descripción: Controlador REST para operaciones CRUD de TblRegional (TBL_REGIONAL)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/tbl-regional")
@RequiredArgsConstructor
public class TblRegionalController {

    private final CrudTblRegionalUseCase crudTblRegionalUseCase;
    private final TblRegionalRestMapper mapper;

    @PostMapping("/create-regional")
    public ResponseEntity<ResponseBody<TblRegionalResponseDto>> create(
            @Valid @RequestBody TblRegionalCreateRequestDto request) {
        log.info("Iniciando creación de regional con código: {}", request.getCode());
        TblRegionalModel model = mapper.toModel(request);
        TblRegionalModel created = crudTblRegionalUseCase.createRegional(model);
        TblRegionalResponseDto response = mapper.toResponse(created);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBody.success("Regional creada exitosamente", response));
    }

    @GetMapping("/get-all-regionals")
    public ResponseEntity<ResponseBody<List<TblRegionalResponseDto>>> listAll() {
        log.info("Consultando listado general de regionales");
        List<TblRegionalResponseDto> response = crudTblRegionalUseCase.listAllRegionals().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(ResponseBody.success("Regionales listadas exitosamente", response));
    }

    @GetMapping("/get-regional/{id}")
    public ResponseEntity<ResponseBody<TblRegionalResponseDto>> getById(@PathVariable Long id) {
        log.info("Consultando regional con ID: {}", id);
        return crudTblRegionalUseCase.getRegionalById(id)
                .map(mapper::toResponse)
                .map(res -> ResponseEntity.ok(ResponseBody.success("Regional encontrada exitosamente", res)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBody.error("404", "No se encontró la regional con ID: " + id)));
    }

    @PutMapping("/update-regional/{id}")
    public ResponseEntity<ResponseBody<TblRegionalResponseDto>> update(@PathVariable Long id,
            @Valid @RequestBody TblRegionalUpdateRequestDto request) {
        log.info("Actualizando regional con ID: {}", id);
        TblRegionalModel model = mapper.toModel(request);
        TblRegionalModel updated = crudTblRegionalUseCase.updateRegional(id, model);
        TblRegionalResponseDto response = mapper.toResponse(updated);
        return ResponseEntity.ok(ResponseBody.success("Regional actualizada exitosamente", response));
    }

    @PutMapping("/delete-regional/{id}")
    public ResponseEntity<ResponseBody<Void>> delete(@PathVariable Long id) {
        log.info("Eliminando regional con ID: {}", id);
        crudTblRegionalUseCase.deleteRegionalById(id);
        return ResponseEntity.ok(ResponseBody.success("Regional eliminada exitosamente", null));
    }

    @GetMapping("/get-paginated-regionals")
    public ResponseEntity<ResponseBody<TblRegionalPageResponseDto>> listPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdDate") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "DESC") Sort.Direction sortDir) {
        log.info("Consultando página de regionales: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy,
                sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));
        Page<TblRegionalModel> pageResult = crudTblRegionalUseCase.getListPageRegional(pageable);
        TblRegionalPageResponseDto response = mapper.toPageResponse(pageResult);
        return ResponseEntity.ok(ResponseBody.success("Regionales paginadas exitosamente", response));
    }
}
