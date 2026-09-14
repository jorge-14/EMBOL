package backofficeapi.infrastructure.adapter.input.rest;

import backofficeapi.application.port.input.CrudAuthUserUseCase;
import backofficeapi.domain.model.AuthUserModel;
import backofficeapi.infrastructure.adapter.input.rest.mapper.AuthUserRestMapper;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthUserCreateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.request.AuthUserUpdateRequestDto;
import backofficeapi.infrastructure.adapter.input.rest.response.AuthUserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Douglas Cristhian Javieri Vino
 * @created 11/09/2026
 */
@RestController
@RequestMapping("/api/v1/auth-user")
public class AuthUserController {

    private final CrudAuthUserUseCase crudAuthUserUseCase;
    private final AuthUserRestMapper authUserRestMapper;

    public AuthUserController(CrudAuthUserUseCase crudAuthUserUseCase, AuthUserRestMapper authUserRestMapper) {
        this.crudAuthUserUseCase = crudAuthUserUseCase;
        this.authUserRestMapper = authUserRestMapper;
    }

    @PostMapping("/create-user")
    public ResponseEntity<AuthUserResponseDto> createUser(@RequestBody AuthUserCreateRequestDto authUserCreateRequest) {
        AuthUserModel authUserModel = authUserRestMapper.toModelCreate(authUserCreateRequest);
        AuthUserModel authUserModelCreate = crudAuthUserUseCase.create(authUserModel);
        AuthUserResponseDto authUserResponseDto = authUserRestMapper.toAuthUserResponseDto(authUserModelCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(authUserResponseDto);
    }

    @PutMapping("/update-user")
    public ResponseEntity<AuthUserResponseDto> updateUser(@RequestBody AuthUserUpdateRequestDto authUserUpdateRequest) {
        AuthUserModel authUserModel = authUserRestMapper.toModelUpdate(authUserUpdateRequest);
        AuthUserModel authUserModelUpdate = crudAuthUserUseCase.update(authUserModel);
        AuthUserResponseDto authUserResponseDto = authUserRestMapper.toAuthUserResponseDto(authUserModelUpdate);
        return ResponseEntity.ok(authUserResponseDto);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<List<AuthUserResponseDto>> getAllUsers() {
        List<AuthUserModel> authUserModelList = crudAuthUserUseCase.findAll();
        List<AuthUserResponseDto> authUserResponseDtoList = authUserRestMapper.toAuthUserResponseDtoList(authUserModelList);
        return ResponseEntity.ok(authUserResponseDtoList);
    }

    @GetMapping("/get-user/{entraId}")
    public ResponseEntity<AuthUserResponseDto> getUserByEntraId(@PathVariable String entraId) {
        AuthUserModel authUserModel = crudAuthUserUseCase.findByEntraId(entraId);
        return ResponseEntity.ok(authUserRestMapper.toAuthUserResponseDto(authUserModel));
    }

    @DeleteMapping("/delete-user/{entraId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String entraId) {
        crudAuthUserUseCase.delete(entraId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/activate-user/{entraId}")
    public ResponseEntity<AuthUserResponseDto> activateUser(@PathVariable String entraId) {
        AuthUserModel authUserModel = crudAuthUserUseCase.activate(entraId);
        return ResponseEntity.ok(authUserRestMapper.toAuthUserResponseDto(authUserModel));
    }

    @PatchMapping("/deactivate-user/{entraId}")
    public ResponseEntity<AuthUserResponseDto> deactivateUser(@PathVariable String entraId) {
        AuthUserModel authUserModel = crudAuthUserUseCase.deactivate(entraId);
        return ResponseEntity.ok(authUserRestMapper.toAuthUserResponseDto(authUserModel));
    }
}
