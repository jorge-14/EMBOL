package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.CrudTblUsuarioUseCase;
import backofficeapi.domain.model.TblUsuarioModel;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponsePage;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblUsuarioRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.TblUsuarioCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.TblUsuarioUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.TblUsuarioResponseDto;
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
 *   Código de Objeto: TblUsuarioController
 *   Descripción: Controlador REST para operaciones CRUD de TblUsuario (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Nomenclatura TblUsuarioController, @Valid y ResponseBody
 *----------------------------------------
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/tbl-usuario")
@RequiredArgsConstructor
public class TblUsuarioController {

    private final CrudTblUsuarioUseCase crudTblUsuarioUseCase;
    private final TblUsuarioRestMapper mapper;

    @PostMapping("/create-user")
    public ResponseEntity<ResponseBody<TblUsuarioResponseDto>> createUser(
            @Valid @RequestBody TblUsuarioCreateRequestDto request) {
        log.info("Iniciando creación de usuario con username: {}", request.getUsername());
        TblUsuarioModel model = mapper.toModelCreate(request);
        TblUsuarioModel created = crudTblUsuarioUseCase.createUser(model);
        TblUsuarioResponseDto response = mapper.toResponse(created);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBody.success("Usuario creado exitosamente", response));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<ResponseBody<TblUsuarioResponseDto>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody TblUsuarioUpdateRequestDto request) {
        log.info("Actualizando usuario con ID: {}", id);
        TblUsuarioModel model = mapper.toModelUpdate(request);
        TblUsuarioModel updated = crudTblUsuarioUseCase.updateUser(id, model);
        TblUsuarioResponseDto response = mapper.toResponse(updated);

        return ResponseEntity.ok(ResponseBody.success("Usuario actualizado exitosamente", response));
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<ResponseBody<List<TblUsuarioResponseDto>>> listAllUsers() {
        log.info("Consultando listado general de usuarios");
        List<TblUsuarioResponseDto> response = mapper.toResponseList(crudTblUsuarioUseCase.listAllUsers());
        return ResponseEntity.ok(ResponseBody.success("Usuarios listados exitosamente", response));
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<ResponseBody<TblUsuarioResponseDto>> getUserById(@PathVariable Long id) {
        log.info("Consultando usuario con ID: {}", id);
        return crudTblUsuarioUseCase.getUserById(id)
                .map(mapper::toResponse)
                .map(res -> ResponseEntity.ok(ResponseBody.success("Usuario encontrado exitosamente", res)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBody.error("404", "No se encontró el usuario con ID: " + id)));
    }

    @GetMapping("/get-user-by-username/{username}")
    public ResponseEntity<ResponseBody<TblUsuarioResponseDto>> getUserByUsername(@PathVariable String username) {
        log.info("Consultando usuario por username: {}", username);
        return crudTblUsuarioUseCase.getUserByUsername(username)
                .map(mapper::toResponse)
                .map(res -> ResponseEntity.ok(ResponseBody.success("Usuario encontrado exitosamente", res)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBody.error("404", "No se encontró el usuario: " + username)));
    }

    @GetMapping("/paginated")
    public ResponseEntity<ResponsePage<TblUsuarioResponseDto>> listPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sortBy", defaultValue = "modifiedDate") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "DESC") Sort.Direction sortDir) {
        log.info("Consultando página de usuarios: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy,
                sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));
        Page<TblUsuarioModel> pageResult = crudTblUsuarioUseCase.getListPageUsers(pageable);
        ResponsePage<TblUsuarioResponseDto> response = ResponsePage.from(pageResult, mapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/delete-user/{id}")
    public ResponseEntity<ResponseBody<Void>> deleteUser(@PathVariable Long id) {
        log.info("Eliminando lógicamente usuario con ID: {}", id);
        crudTblUsuarioUseCase.deleteUserById(id);
        return ResponseEntity.ok(ResponseBody.success("Usuario eliminado exitosamente", null));
    }

    @PutMapping("/activate-user/{id}")
    public ResponseEntity<ResponseBody<TblUsuarioResponseDto>> activateUser(@PathVariable Long id) {
        log.info("Activando usuario con ID: {}", id);
        TblUsuarioModel activated = crudTblUsuarioUseCase.activateUserById(id);
        return ResponseEntity.ok(ResponseBody.success("Usuario activado exitosamente", mapper.toResponse(activated)));
    }

    @PutMapping("/deactivate-user/{id}")
    public ResponseEntity<ResponseBody<TblUsuarioResponseDto>> deactivateUser(@PathVariable Long id) {
        log.info("Desactivando usuario con ID: {}", id);
        TblUsuarioModel deactivated = crudTblUsuarioUseCase.deactivateUserById(id);
        return ResponseEntity
                .ok(ResponseBody.success("Usuario desactivado exitosamente", mapper.toResponse(deactivated)));
    }
}
