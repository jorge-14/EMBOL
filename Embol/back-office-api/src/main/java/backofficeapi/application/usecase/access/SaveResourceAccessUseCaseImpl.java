package backofficeapi.application.usecase.access;

import backofficeapi.application.port.input.access.SaveResourceAccessUseCase;
import backofficeapi.application.port.output.TblAccessRepositoryPort;
import backofficeapi.infrastructure.adapter.input.rest.request.tblAccess.ResourceSavePermissionDto;
import backofficeapi.infrastructure.adapter.input.rest.request.tblAccess.SaveResourceAccessRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: SaveResourceAccessUseCaseImpl
 *   Descripción: Implementación del caso de uso para guardar asignaciones usando directamente SaveResourceAccessRequestDto
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial simplificada
 *----------------------------------------
 */
@Service
@RequiredArgsConstructor
public class SaveResourceAccessUseCaseImpl implements SaveResourceAccessUseCase {

    private final TblAccessRepositoryPort accessRepositoryPort;

    @Override
    public void saveAccess(SaveResourceAccessRequestDto request) {
        String normalizedMode = request.getMode() != null ? request.getMode().trim().toUpperCase() : "ROL";
        Long id = request.getId();

        List<Long> grantedResourceIds = new ArrayList<>();
        if (request.getPermissions() != null) {
            for (ResourceSavePermissionDto perm : request.getPermissions()) {
                if (perm.getResourceId() != null && perm.getGrantedActionCodes() != null && !perm.getGrantedActionCodes().isEmpty()) {
                    grantedResourceIds.add(perm.getResourceId());
                }
            }
        }

        if ("ROL".equals(normalizedMode)) {
            accessRepositoryPort.saveRoleResources(id, grantedResourceIds);
        } else {
            accessRepositoryPort.saveGroupResources(id, grantedResourceIds);
        }
    }
}
