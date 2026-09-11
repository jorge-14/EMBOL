package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.CrudAuthRoleUseCase;
import backofficeapi.domain.model.AuthRoleModel;
import backofficeapi.infrastructure.adapter.input.rest.mapper.AuthRoleRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthRoleRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthRoleResponseDto;
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
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */


@RestController
@RequestMapping("/api/v1/auth-role")
public class AuthRoleController {

    private final CrudAuthRoleUseCase crudAuthRoleUseCase;
    private final AuthRoleRestMapper authRoleRestMapper;

    public AuthRoleController(CrudAuthRoleUseCase crudAuthRoleUseCase, AuthRoleRestMapper authRoleRestMapper) {
        this.crudAuthRoleUseCase = crudAuthRoleUseCase;
        this.authRoleRestMapper = authRoleRestMapper;
    }

    @PostMapping("/create-role")
    public ResponseEntity<AuthRoleResponseDto> createRole(@RequestBody AuthRoleRequestDto authRoleRequestDto) {
        AuthRoleModel authRoleModel = authRoleRestMapper.toModel(authRoleRequestDto);
        AuthRoleModel authRoleModelCreate = crudAuthRoleUseCase.createRole(authRoleModel);
        AuthRoleResponseDto authRoleResponseDto = authRoleRestMapper.toAuthRoleResponseDto(authRoleModelCreate);
        return ResponseEntity.ok(authRoleResponseDto);
    }
}
