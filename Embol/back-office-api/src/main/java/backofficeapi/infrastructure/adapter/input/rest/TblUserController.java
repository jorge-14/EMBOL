package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.user.ChangeStatusUserUseCase;
import backofficeapi.application.port.input.user.CrudTblUserUseCase;
import backofficeapi.domain.model.TblUserModel;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponseBody;
import backofficeapi.infrastructure.adapter.input.rest.dto.ResponsePage;
import backofficeapi.infrastructure.adapter.input.rest.mapper.TblUserRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.tblUser.TblUserCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.tblUser.TblUserUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblUser.TblUserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblUserController
 *   Descripción: Controlador REST para operaciones CRUD de TblUser (TBL_USUARIO)
 *   Author Prog: Douglas Javieri / Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Douglas Javieri | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Nomenclatura TblUsuarioController
 *----------------------------------------
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/tbl-usuario")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Servicios para la administración y gestión de usuarios del sistema (TBL_USUARIO)")
public class TblUserController {

        private final CrudTblUserUseCase crudTblUserUseCase;
        private final ChangeStatusUserUseCase changeStatusUserUseCase;
        private final TblUserRestMapper mapper;

        @PostMapping("/create-user")
        @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo usuario en el sistema con sus roles y grupos asignados.", tags = {
                        "Usuarios" }, responses = {
                                        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
                                        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                        })
        public ResponseEntity<ResponseBody<TblUserResponseDto>> createUser(
                        @Valid @RequestBody TblUserCreateRequestDto request) {
                log.info("Iniciando creación de usuario con username: {}", request.getUsername());
                TblUserModel model = mapper.toModelCreate(request);
                TblUserModel created = crudTblUserUseCase.createUser(model);
                TblUserResponseDto response = mapper.toResponse(created);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(ResponseBody.success("Usuario creado exitosamente", response));
        }

        @PutMapping("/update-user/{id}")
        @Operation(summary = "Actualizar usuario", description = "Actualiza los datos, roles y grupos de un usuario existente.", tags = {
                        "Usuarios" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
                                        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
                                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                        })
        public ResponseEntity<ResponseBody<TblUserResponseDto>> updateUser(
                        @Parameter(description = "ID del usuario", example = "1", required = true) @PathVariable Long id,
                        @Valid @RequestBody TblUserUpdateRequestDto request) {
                log.info("Actualizando usuario con ID: {}", id);
                TblUserModel model = mapper.toModelUpdate(request);
                TblUserModel updated = crudTblUserUseCase.updateUser(id, model);
                TblUserResponseDto response = mapper.toResponse(updated);

                return ResponseEntity.ok(ResponseBody.success("Usuario actualizado exitosamente", response));
        }

        @GetMapping("/get-all-users")
        @Operation(summary = "Listar todos los usuarios", description = "Obtiene el listado completo de usuarios activos.", tags = {
                        "Usuarios" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Usuarios listados exitosamente"),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                        })
        public ResponseEntity<ResponseBody<List<TblUserResponseDto>>> listAllUsers() {
                log.info("Consultando listado general de usuarios");
                List<TblUserResponseDto> response = mapper.toResponseList(crudTblUserUseCase.listAllUsers());
                return ResponseEntity.ok(ResponseBody.success("Usuarios listados exitosamente", response));
        }

        @GetMapping("/get-user/{id}")
        @Operation(summary = "Obtener usuario por ID", description = "Busca un usuario específico por su identificador numérico.", tags = {
                        "Usuarios" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
                                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                        })
        public ResponseEntity<ResponseBody<TblUserResponseDto>> getUserById(
                        @Parameter(description = "ID del usuario", example = "1", required = true) @PathVariable Long id) {
                log.info("Consultando usuario con ID: {}", id);
                return crudTblUserUseCase.getUserById(id)
                                .map(mapper::toResponse)
                                .map(res -> ResponseEntity
                                                .ok(ResponseBody.success("Usuario encontrado exitosamente", res)))
                                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(ResponseBody.error("404",
                                                                "No se encontró el usuario con ID: " + id)));
        }

        @GetMapping("/get-user-by-username/{username}")
        @Operation(summary = "Obtener usuario por nombre de usuario", description = "Busca un usuario específico por su username corporativo.", tags = {
                        "Usuarios" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
                                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                        })
        public ResponseEntity<ResponseBody<TblUserResponseDto>> getUserByUsername(
                        @Parameter(description = "Nombre de usuario", example = "cledezma", required = true) @PathVariable String username) {
                log.info("Consultando usuario por username: {}", username);
                return crudTblUserUseCase.getUserByUsername(username)
                                .map(mapper::toResponse)
                                .map(res -> ResponseEntity
                                                .ok(ResponseBody.success("Usuario encontrado exitosamente", res)))
                                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                .body(ResponseBody.error("404",
                                                                "No se encontró el usuario: " + username)));
        }

        @GetMapping("/paginated-user")
        @Operation(summary = "Listar usuarios paginados", description = "Obtiene una página de usuarios con ordenamiento y paginación configurable.", tags = {
                        "Usuarios" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Página de usuarios obtenida exitosamente"),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                        })
        public ResponseEntity<ResponsePage<TblUserResponseDto>> listPage(
                        @Parameter(description = "Número de página (0-index)", example = "0") @RequestParam(value = "page", defaultValue = "0") int page,
                        @Parameter(description = "Cantidad de registros por página", example = "20") @RequestParam(value = "size", defaultValue = "20") int size,
                        @Parameter(description = "Campo de ordenamiento", example = "modifiedDate") @RequestParam(value = "sortBy", defaultValue = "modifiedDate") String sortBy,
                        @Parameter(description = "Dirección de orden (ASC o DESC)", example = "DESC") @RequestParam(value = "sortDir", defaultValue = "DESC") Sort.Direction sortDir) {
                log.info("Consultando página de usuarios: page={}, size={}, sortBy={}, sortDir={}", page, size, sortBy,
                                sortDir);
                Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, sortBy));
                Page<TblUserModel> pageResult = crudTblUserUseCase.getListPageUsers(pageable);
                ResponsePage<TblUserResponseDto> response = ResponsePage.from(pageResult, mapper::toResponse);
                return ResponseEntity.ok(response);
        }

        @PutMapping("/delete-user/{id}")
        @Operation(summary = "Eliminar usuario", description = "Realiza la eliminación lógica (soft-delete) de un usuario por su ID.", tags = {
                        "Usuarios" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
                                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                        })
        public ResponseEntity<ResponseBody<Void>> deleteUser(
                        @Parameter(description = "ID del usuario", example = "1", required = true) @PathVariable Long id) {
                log.info("Eliminando lógicamente usuario con ID: {}", id);
                crudTblUserUseCase.deleteUserById(id);
                return ResponseEntity.ok(ResponseBody.success("Usuario eliminado exitosamente", null));
        }

        @PutMapping("/activate-user/{id}")
        @Operation(summary = "Activar usuario", description = "Cambia el estado del usuario a ACTIVO.", tags = {
                        "Usuarios" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Usuario activado exitosamente"),
                                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                        })
        public ResponseEntity<ResponseBody<TblUserResponseDto>> activateUser(
                        @Parameter(description = "ID del usuario", example = "1", required = true) @PathVariable Long id) {
                log.info("Activando usuario con ID: {}", id);
                TblUserModel activated = changeStatusUserUseCase.activateUserById(id);
                return ResponseEntity.ok(
                                ResponseBody.success("Usuario activado exitosamente", mapper.toResponse(activated)));
        }

        @PutMapping("/deactivate-user/{id}")
        @Operation(summary = "Desactivar usuario", description = "Cambia el estado del usuario a INACTIVO.", tags = {
                        "Usuarios" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Usuario desactivado exitosamente"),
                                        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
                                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                        })
        public ResponseEntity<ResponseBody<TblUserResponseDto>> deactivateUser(
                        @Parameter(description = "ID del usuario", example = "1", required = true) @PathVariable Long id) {
                log.info("Desactivando usuario con ID: {}", id);
                TblUserModel deactivated = changeStatusUserUseCase.deactivateUserById(id);
                return ResponseEntity
                                .ok(ResponseBody.success("Usuario desactivado exitosamente",
                                                mapper.toResponse(deactivated)));
        }
}
