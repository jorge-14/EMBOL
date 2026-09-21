package backofficeapi.application.usecase.resource;

import backofficeapi.application.port.input.resource.SaveResourceAccessUseCase;
import backofficeapi.application.port.output.TblResourceRepositoryPort;
import backofficeapi.infrastructure.adapter.input.rest.request.tblResource.ResourceSavePermissionDto;
import backofficeapi.infrastructure.adapter.input.rest.request.tblResource.SaveResourceAccessRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: SaveResourceAccessUseCaseImpl
 *   Descripción: Implementación del caso de uso para guardar asignaciones de accesos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial en módulo resource
 *----------------------------------------
 */
@Service
@RequiredArgsConstructor
public class SaveResourceAccessUseCaseImpl implements SaveResourceAccessUseCase {

    private final TblResourceRepositoryPort resourceRepositoryPort;

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
            resourceRepositoryPort.saveRoleResources(id, grantedResourceIds);
        } else {
            resourceRepositoryPort.saveGroupResources(id, grantedResourceIds);
        }
    }
}
