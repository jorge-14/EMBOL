package backofficeapi.application.usecase.resource;

import backofficeapi.application.port.input.resource.GetResourceAccessUseCase;
import backofficeapi.application.port.output.TblResourceRepositoryPort;
import backofficeapi.infrastructure.adapter.input.rest.response.tblResource.ActionPermissionDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblResource.GroupPermissionDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblResource.ResourceAccessResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblResource.ResourcePermissionDto;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblAccion;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecurso;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecursoAccion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: GetResourceAccessUseCaseImpl
 *   Descripción: Implementación del caso de uso para consultar la matriz de accesos y recursos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial en módulo resource
 *----------------------------------------
 */
@Service
@RequiredArgsConstructor
public class GetResourceAccessUseCaseImpl implements GetResourceAccessUseCase {

    private final TblResourceRepositoryPort resourceRepositoryPort;

    @Override
    public ResourceAccessResponseDto getAccess(String mode, Long id) {
        String normalizedMode = mode != null ? mode.trim().toUpperCase() : "ROL";

        String targetName = "ROL".equals(normalizedMode)
                ? resourceRepositoryPort.getRoleName(id)
                : resourceRepositoryPort.getGroupName(id);

        List<Long> grantedResourceIds = "ROL".equals(normalizedMode)
                ? resourceRepositoryPort.getGrantedResourceIdsForRole(id)
                : resourceRepositoryPort.getGrantedResourceIdsForGroup(id);

        Set<Long> grantedSet = new HashSet<>(grantedResourceIds != null ? grantedResourceIds : List.of());

        List<TblAccion> allActions = resourceRepositoryPort.getAllActions();
        List<ActionPermissionDto> actionDtos = allActions.stream()
                .map(this::mapToActionDto)
                .toList();

        List<TblRecursoAccion> supportedResourceActions = resourceRepositoryPort.getSupportedResourceActions();
        Set<String> supportedSet = new HashSet<>();
        for (TblRecursoAccion ra : supportedResourceActions) {
            if (ra.getIIdRecurso() != null && ra.getIIdAccion() != null) {
                supportedSet
                        .add(ra.getIIdRecurso().getIIdRecurso() + ":" + ra.getIIdAccion().getSCodigo().toUpperCase());
            }
        }

        List<TblRecurso> parents = resourceRepositoryPort.getParentResources();
        List<GroupPermissionDto> groupDtos = new ArrayList<>();
        int totalGranted = 0;

        for (TblRecurso parent : parents) {
            List<TblRecurso> children = resourceRepositoryPort.getChildResources(parent.getIIdRecurso());
            List<ResourcePermissionDto> resourceDtos = new ArrayList<>();

            for (TblRecurso child : children) {
                Map<String, Boolean> permissionsMap = new LinkedHashMap<>();
                boolean isResourceGranted = grantedSet.contains(child.getIIdRecurso());

                for (ActionPermissionDto action : actionDtos) {
                    String key = child.getIIdRecurso() + ":" + action.getKey().toUpperCase();
                    if (!supportedSet.contains(key)) {
                        permissionsMap.put(action.getKey(), null);
                    } else if (isResourceGranted) {
                        permissionsMap.put(action.getKey(), true);
                        totalGranted++;
                    } else {
                        permissionsMap.put(action.getKey(), false);
                    }
                }

                resourceDtos.add(ResourcePermissionDto.builder()
                        .id(child.getIIdRecurso())
                        .name(child.getSNombre())
                        .icon(child.getIcono())
                        .permissions(permissionsMap)
                        .build());
            }

            groupDtos.add(GroupPermissionDto.builder()
                    .id(parent.getIIdRecurso())
                    .name(parent.getSNombre())
                    .resources(resourceDtos)
                    .build());
        }

        return ResourceAccessResponseDto.builder()
                .mode(normalizedMode.toLowerCase())
                .id(id)
                .name(targetName)
                .totalGrantedPermissions(totalGranted)
                .actions(actionDtos)
                .groups(groupDtos)
                .build();
    }

    private ActionPermissionDto mapToActionDto(TblAccion accion) {
        String code = accion.getSCodigo() != null ? accion.getSCodigo().toUpperCase() : "";
        String name = accion.getSNombre() != null ? accion.getSNombre() : code;

        return ActionPermissionDto.builder()
                .key(code)
                .label(name)
                .build();
    }
}
