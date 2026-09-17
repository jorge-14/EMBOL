package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.group.CrudTblGroupUseCase;
import backofficeapi.domain.model.TblGroupModel;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponsePage;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblGroupRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.tblGroup.TblGroupRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblGroup.TblGroupResponseDto;
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

    private final CrudTblGroupUseCase crudTblGroupUseCase;
    private final TblGroupRestMapper tblGroupRestMapper;

    public TblGroupController(CrudTblGroupUseCase crudTblGroupUseCase, TblGroupRestMapper tblGroupRestMapper) {
        this.crudTblGroupUseCase = crudTblGroupUseCase;
        this.tblGroupRestMapper = tblGroupRestMapper;
    }

    @PostMapping("/create-group")
    public ResponseEntity<TblGroupResponseDto> create(@RequestBody TblGroupRequestDto tblGroupRequestDto) {

        TblGroupModel model = tblGroupRestMapper.toModel(tblGroupRequestDto);
        TblGroupModel created = crudTblGroupUseCase.createGroup(model);
        TblGroupResponseDto response  = tblGroupRestMapper.toResponse(created);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated-group")
    public ResponseEntity<ResponsePage<TblGroupResponseDto>> listPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sortBy", defaultValue = "modifiedDate") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "DESC") Sort.Direction sortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));
        Page<TblGroupModel> pageResult = crudTblGroupUseCase.pageListGroup(pageable);
        ResponsePage<TblGroupResponseDto> response = ResponsePage.from(pageResult, tblGroupRestMapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update-group/{id}")
    public ResponseEntity<TblGroupResponseDto> updateGroup(@PathVariable Long id,
                                                           @RequestBody TblGroupRequestDto tblGroupRequestDto) {
        TblGroupModel model = tblGroupRestMapper.toModel(tblGroupRequestDto);
        TblGroupModel update = crudTblGroupUseCase.updateGroup(id, model);
        TblGroupResponseDto response  = tblGroupRestMapper.toResponse(update);
        return ResponseEntity.ok(response);
    }
}
