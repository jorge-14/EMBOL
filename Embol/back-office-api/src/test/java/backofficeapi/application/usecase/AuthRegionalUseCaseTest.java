package backofficeapi.application.usecase;

import backofficeapi.application.port.output.AuthRegionalRepositoryPort;
import backofficeapi.domain.exception.AuthRegionalNotFoundException;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.model.AuthRegionalModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthRegionalUseCaseTest
 *   Descripción: Pruebas unitarias para CrudAuthRegionalUseCaseImpl
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@ExtendWith(MockitoExtension.class)
class AuthRegionalUseCaseTest {

    @Mock
    private AuthRegionalRepositoryPort authRegionalRepositoryPort;

    @InjectMocks
    private CrudAuthRegionalUseCaseImpl crudAuthRegionalUseCase;

    private AuthRegionalModel sampleModel;

    @BeforeEach
    void setUp() {
        sampleModel = AuthRegionalModel.builder()
                .id(1L)
                .code("LPZ")
                .name("La Paz")
                .description("Regional La Paz y El Alto")
                .address("Av. Montes 123")
                .deleted(false)
                .build();
    }

    @Test
    @DisplayName("Debe crear una regional correctamente con address")
    void createRegional_Success() {
        when(authRegionalRepositoryPort.getRegionalByCode("LPZ")).thenReturn(Optional.empty());
        when(authRegionalRepositoryPort.saveRegional(any(AuthRegionalModel.class))).thenReturn(sampleModel);

        AuthRegionalModel toCreate = AuthRegionalModel.builder()
                .code("lpz")
                .name("La Paz")
                .description("Regional La Paz y El Alto")
                .address("Av. Montes 123")
                .build();

        AuthRegionalModel result = crudAuthRegionalUseCase.createRegional(toCreate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("LPZ", result.getCode());
        assertEquals("Av. Montes 123", result.getAddress());
        assertFalse(result.getDeleted());
        verify(authRegionalRepositoryPort, times(1)).saveRegional(any(AuthRegionalModel.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el código ya existe")
    void createRegional_DuplicateCode_ThrowsException() {
        when(authRegionalRepositoryPort.getRegionalByCode("LPZ")).thenReturn(Optional.of(sampleModel));

        AuthRegionalModel toCreate = AuthRegionalModel.builder()
                .code("LPZ")
                .name("La Paz Duplicada")
                .build();

        assertThrows(BusinessApiException.class, () -> crudAuthRegionalUseCase.createRegional(toCreate));
        verify(authRegionalRepositoryPort, never()).saveRegional(any(AuthRegionalModel.class));
    }

    @Test
    @DisplayName("Debe listar todas las regionales")
    void listAllRegionals_Success() {
        when(authRegionalRepositoryPort.listAllRegionals()).thenReturn(List.of(sampleModel));

        List<AuthRegionalModel> result = crudAuthRegionalUseCase.listAllRegionals();

        assertEquals(1, result.size());
        assertEquals("La Paz", result.get(0).getName());
    }

    @Test
    @DisplayName("Debe obtener una regional por ID")
    void getRegionalById_Success() {
        when(authRegionalRepositoryPort.getRegionalById(1L)).thenReturn(Optional.of(sampleModel));

        Optional<AuthRegionalModel> result = crudAuthRegionalUseCase.getRegionalById(1L);

        assertTrue(result.isPresent());
        assertEquals("LPZ", result.get().getCode());
    }

    @Test
    @DisplayName("Debe actualizar una regional existente incluyendo address")
    void updateRegional_Success() {
        when(authRegionalRepositoryPort.getRegionalById(1L)).thenReturn(Optional.of(sampleModel));
        when(authRegionalRepositoryPort.saveRegional(any(AuthRegionalModel.class))).thenReturn(sampleModel);

        AuthRegionalModel updateData = AuthRegionalModel.builder()
                .name("La Paz Modificada")
                .description("Nueva descripción")
                .address("Calle 21 de Calacoto")
                .deleted(false)
                .build();

        AuthRegionalModel result = crudAuthRegionalUseCase.updateRegional(1L, updateData);

        assertNotNull(result);
        verify(authRegionalRepositoryPort, times(1)).saveRegional(any(AuthRegionalModel.class));
    }

    @Test
    @DisplayName("Debe realizar eliminación lógica (soft delete con deleted=true) de una regional por ID")
    void deleteRegionalById_Success() {
        when(authRegionalRepositoryPort.getRegionalById(1L)).thenReturn(Optional.of(sampleModel));
        when(authRegionalRepositoryPort.saveRegional(any(AuthRegionalModel.class))).thenReturn(sampleModel);

        assertDoesNotThrow(() -> crudAuthRegionalUseCase.deleteRegionalById(1L));
        assertTrue(sampleModel.getDeleted());
        verify(authRegionalRepositoryPort, times(1)).saveRegional(sampleModel);
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar eliminar una regional inexistente")
    void deleteRegionalById_NotFound_ThrowsException() {
        when(authRegionalRepositoryPort.getRegionalById(99L)).thenReturn(Optional.empty());

        assertThrows(AuthRegionalNotFoundException.class, () -> crudAuthRegionalUseCase.deleteRegionalById(99L));
        verify(authRegionalRepositoryPort, never()).saveRegional(any(AuthRegionalModel.class));
    }
}
