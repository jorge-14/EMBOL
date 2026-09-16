package backofficeapi.application.usecase;

import backofficeapi.application.port.output.TblRegionalRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.exception.TblRegionalNotFoundException;
import backofficeapi.domain.model.TblRegionalModel;
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
 *   Código de Objeto: TblRegionalUseCaseTest
 *   Descripción: Pruebas unitarias para CrudTblRegionalUseCaseImpl
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *   16.09.2026 | Camila Ledezma | Actualización a TblRegional
 *----------------------------------------
 */

@ExtendWith(MockitoExtension.class)
class TblRegionalUseCaseTest {

    @Mock
    private TblRegionalRepositoryPort tblRegionalRepositoryPort;

    @InjectMocks
    private CrudTblRegionalUseCaseImpl crudTblRegionalUseCase;

    private TblRegionalModel sampleModel;

    @BeforeEach
    void setUp() {
        sampleModel = TblRegionalModel.builder()
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
        when(tblRegionalRepositoryPort.getRegionalByCode("LPZ")).thenReturn(Optional.empty());
        when(tblRegionalRepositoryPort.saveRegional(any(TblRegionalModel.class))).thenReturn(sampleModel);

        TblRegionalModel toCreate = TblRegionalModel.builder()
                .code("lpz")
                .name("La Paz")
                .description("Regional La Paz y El Alto")
                .address("Av. Montes 123")
                .build();

        TblRegionalModel result = crudTblRegionalUseCase.createRegional(toCreate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("LPZ", result.getCode());
        assertEquals("Av. Montes 123", result.getAddress());
        assertFalse(result.getDeleted());
        verify(tblRegionalRepositoryPort, times(1)).saveRegional(any(TblRegionalModel.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el código ya existe")
    void createRegional_DuplicateCode_ThrowsException() {
        when(tblRegionalRepositoryPort.getRegionalByCode("LPZ")).thenReturn(Optional.of(sampleModel));

        TblRegionalModel toCreate = TblRegionalModel.builder()
                .code("LPZ")
                .name("La Paz Duplicada")
                .build();

        assertThrows(BusinessApiException.class, () -> crudTblRegionalUseCase.createRegional(toCreate));
        verify(tblRegionalRepositoryPort, never()).saveRegional(any(TblRegionalModel.class));
    }

    @Test
    @DisplayName("Debe listar todas las regionales")
    void listAllRegionals_Success() {
        when(tblRegionalRepositoryPort.listAllRegionals()).thenReturn(List.of(sampleModel));

        List<TblRegionalModel> result = crudTblRegionalUseCase.listAllRegionals();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("La Paz", result.get(0).getName());
    }

    @Test
    @DisplayName("Debe obtener una regional por ID")
    void getRegionalById_Success() {
        when(tblRegionalRepositoryPort.getRegionalById(1L)).thenReturn(Optional.of(sampleModel));

        Optional<TblRegionalModel> result = crudTblRegionalUseCase.getRegionalById(1L);

        assertTrue(result.isPresent());
        assertEquals("LPZ", result.get().getCode());
    }

    @Test
    @DisplayName("Debe actualizar una regional existente incluyendo address")
    void updateRegional_Success() {
        when(tblRegionalRepositoryPort.getRegionalById(1L)).thenReturn(Optional.of(sampleModel));
        when(tblRegionalRepositoryPort.saveRegional(any(TblRegionalModel.class))).thenReturn(sampleModel);

        TblRegionalModel updateData = TblRegionalModel.builder()
                .name("La Paz Modificada")
                .description("Nueva descripción")
                .address("Calle 21 de Calacoto")
                .deleted(false)
                .build();

        TblRegionalModel result = crudTblRegionalUseCase.updateRegional(1L, updateData);

        assertNotNull(result);
        verify(tblRegionalRepositoryPort, times(1)).saveRegional(any(TblRegionalModel.class));
    }

    @Test
    @DisplayName("Debe realizar eliminación lógica (soft delete con deleted=true) de una regional por ID")
    void deleteRegionalById_Success() {
        when(tblRegionalRepositoryPort.getRegionalById(1L)).thenReturn(Optional.of(sampleModel));
        when(tblRegionalRepositoryPort.saveRegional(any(TblRegionalModel.class))).thenReturn(sampleModel);

        assertDoesNotThrow(() -> crudTblRegionalUseCase.deleteRegionalById(1L));
        assertTrue(sampleModel.getDeleted());
        verify(tblRegionalRepositoryPort, times(1)).saveRegional(sampleModel);
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar eliminar una regional inexistente")
    void deleteRegionalById_NotFound_ThrowsException() {
        when(tblRegionalRepositoryPort.getRegionalById(99L)).thenReturn(Optional.empty());

        assertThrows(TblRegionalNotFoundException.class, () -> crudTblRegionalUseCase.deleteRegionalById(99L));
        verify(tblRegionalRepositoryPort, never()).saveRegional(any(TblRegionalModel.class));
    }
}
