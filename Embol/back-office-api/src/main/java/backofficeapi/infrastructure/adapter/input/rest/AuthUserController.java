package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.CrudAuthUserUseCase;
import backofficeapi.domain.model.AuthUserModel;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;
import backofficeapi.infrastructure.adapter.input.rest.mapper.AuthUserRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthUserCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthUserUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthUserPageResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthUserResponseDto;
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
import org.springframework.web.bind.annotation.PatchMapping;
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
 *   Código de Objeto: AuthUserController
 *   Descripción: Controlador REST para operaciones CRUD de AuthUser (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Estandarización a ResponseBody, @Valid y TBL_USUARIO
 *----------------------------------------
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/auth-user")
@RequiredArgsConstructor
public class AuthUserController {

    private final CrudAuthUserUseCase crudAuthUserUseCase;
    private final AuthUserRestMapper mapper;

    @PostMapping("/create-user")
    public ResponseEntity<ResponseBody<AuthUserResponseDto>> createUser(
            @Valid @RequestBody AuthUserCreateRequestDto request) {
        log.info("Iniciando creación de usuario con username: {}", request.getUsername());
        AuthUserModel model = mapper.toModelCreate(request);
        AuthUserModel created = crudAuthUserUseCase.createUser(model);
        AuthUserResponseDto response = mapper.toResponse(created);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseBody.success("Usuario creado exitosamente", response));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<ResponseBody<AuthUserResponseDto>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody AuthUserUpdateRequestDto request) {
        log.info("Actualizando usuario con ID: {}", id);
        AuthUserModel model = mapper.toModelUpdate(request);
        AuthUserModel updated = crudAuthUserUseCase.updateUser(id, model);
        AuthUserResponseDto response = mapper.toResponse(updated);

        return ResponseEntity.ok(ResponseBody.success("Usuario actualizado exitosamente", response));
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<ResponseBody<List<AuthUserResponseDto>>> listAllUsers() {
        log.info("Consultando listado general de usuarios");
        List<AuthUserResponseDto> response = mapper.toResponseList(crudAuthUserUseCase.listAllUsers());
        return ResponseEntity.ok(ResponseBody.success("Usuarios listados exitosamente", response));
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<ResponseBody<AuthUserResponseDto>> getUserById(@PathVariable Long id) {
        log.info("Consultando usuario con ID: {}", id);
        return crudAuthUserUseCase.getUserById(id)
                .map(mapper::toResponse)
                .map(res -> ResponseEntity.ok(ResponseBody.success("Usuario encontrado exitosamente", res)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBody.error("404", "No se encontró el usuario con ID: " + id)));
    }

    @GetMapping("/get-user-by-username/{username}")
    public ResponseEntity<ResponseBody<AuthUserResponseDto>> getUserByUsername(@PathVariable String username) {
        log.info("Consultando usuario por username: {}", username);
        return crudAuthUserUseCase.getUserByUsername(username)
                .map(mapper::toResponse)
                .map(res -> ResponseEntity.ok(ResponseBody.success("Usuario encontrado exitosamente", res)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseBody.error("404", "No se encontró el usuario: " + username)));
    }

    @GetMapping("/get-paginated-users")
    public ResponseEntity<ResponseBody<AuthUserPageResponseDto>> listPageUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdDate") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "DESC") Sort.Direction sortDir) {
        log.info("Consultando página de usuarios: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy, sortDir);
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));
        Page<AuthUserModel> pageResult = crudAuthUserUseCase.getListPageUsers(pageable);
        AuthUserPageResponseDto response = mapper.toPageResponse(pageResult);
        return ResponseEntity.ok(ResponseBody.success("Usuarios paginados exitosamente", response));
    }

    @PutMapping("/delete-user/{id}")
    public ResponseEntity<ResponseBody<Void>> deleteUser(@PathVariable Long id) {
        log.info("Eliminando lógicamente usuario con ID: {}", id);
        crudAuthUserUseCase.deleteUserById(id);
        return ResponseEntity.ok(ResponseBody.success("Usuario eliminado exitosamente", null));
    }

    @PutMapping("/activate-user/{id}")
    public ResponseEntity<ResponseBody<AuthUserResponseDto>> activateUser(@PathVariable Long id) {
        log.info("Activando usuario con ID: {}", id);
        AuthUserModel activated = crudAuthUserUseCase.activateUserById(id);
        return ResponseEntity.ok(ResponseBody.success("Usuario activado exitosamente", mapper.toResponse(activated)));
    }

    @PutMapping("/deactivate-user/{id}")
    public ResponseEntity<ResponseBody<AuthUserResponseDto>> deactivateUser(@PathVariable Long id) {
        log.info("Desactivando usuario con ID: {}", id);
        AuthUserModel deactivated = crudAuthUserUseCase.deactivateUserById(id);
        return ResponseEntity.ok(ResponseBody.success("Usuario desactivado exitosamente", mapper.toResponse(deactivated)));
    }
}
