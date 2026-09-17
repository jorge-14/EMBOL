package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.group.CrudTblGroupUseCase;
import backofficeapi.domain.model.TblGroupModel;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblGroupRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.tblGroup.TblGroupRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblGroup.TblGroupResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
