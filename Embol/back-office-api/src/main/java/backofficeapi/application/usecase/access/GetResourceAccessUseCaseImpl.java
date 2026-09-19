package backofficeapi.application.usecase.access;

import backofficeapi.application.port.input.access.GetResourceAccessUseCase;
import backofficeapi.application.port.output.TblAccessRepositoryPort;
import backofficeapi.infrastructure.adapter.input.rest.response.tblAccess.ActionPermissionDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblAccess.GroupPermissionDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblAccess.ResourceAccessResponseDto;
import backofficeapi.infrastructure.adapter.input.rest.response.tblAccess.ResourcePermissionDto;
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
 *   Descripción: Implementación del caso de uso para consultar accesos usando directamente los DTOs
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial simplificada
 *----------------------------------------
 */
@Service
@RequiredArgsConstructor
public class GetResourceAccessUseCaseImpl implements GetResourceAccessUseCase {

    private final TblAccessRepositoryPort accessRepositoryPort;

    @Override
    public ResourceAccessResponseDto getAccess(String mode, Long id) {
        String normalizedMode = mode != null ? mode.trim().toUpperCase() : "ROL";

        String targetName = "ROL".equals(normalizedMode)
                ? accessRepositoryPort.getRoleName(id)
                : accessRepositoryPort.getGroupName(id);

        List<Long> grantedResourceIds = "ROL".equals(normalizedMode)
                ? accessRepositoryPort.getGrantedResourceIdsForRole(id)
                : accessRepositoryPort.getGrantedResourceIdsForGroup(id);

        Set<Long> grantedSet = new HashSet<>(grantedResourceIds != null ? grantedResourceIds : List.of());

        List<TblAccion> allActions = accessRepositoryPort.getAllActions();
        List<ActionPermissionDto> actionDtos = allActions.stream()
                .map(this::mapToActionDto)
                .toList();

        List<TblRecursoAccion> supportedResourceActions = accessRepositoryPort.getSupportedResourceActions();
        Set<String> supportedSet = new HashSet<>();
        for (TblRecursoAccion ra : supportedResourceActions) {
            if (ra.getIIdRecurso() != null && ra.getIIdAccion() != null) {
                supportedSet
                        .add(ra.getIIdRecurso().getIIdRecurso() + ":" + ra.getIIdAccion().getSCodigo().toUpperCase());
            }
        }

        List<TblRecurso> parents = accessRepositoryPort.getParentResources();
        List<GroupPermissionDto> groupDtos = new ArrayList<>();
        int totalGranted = 0;

        for (TblRecurso parent : parents) {
            List<TblRecurso> children = accessRepositoryPort.getChildResources(parent.getIIdRecurso());
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
        String colorClass = "text-gray-600";
        String activeColorClass = "bg-gray-500 border-gray-600";

        switch (code) {
            case "VER" -> {
                colorClass = "text-green-600";
                activeColorClass = "bg-green-500 border-green-600";
            }
            case "CREAR" -> {
                colorClass = "text-blue-600";
                activeColorClass = "bg-blue-500 border-blue-600";
            }
            case "MODIFICAR" -> {
                colorClass = "text-amber-500";
                activeColorClass = "bg-amber-500 border-amber-600";
            }
            case "ELIMINAR" -> {
                colorClass = "text-red-600";
                activeColorClass = "bg-red-500 border-red-600";
            }
            case "DESCARGAR" -> {
                colorClass = "text-purple-600";
                activeColorClass = "bg-purple-500 border-purple-600";
            }
            case "EXPORTAR" -> {
                colorClass = "text-emerald-500";
                activeColorClass = "bg-emerald-500 border-emerald-600";
            }
            case "APROBAR" -> {
                colorClass = "text-rose-500";
                activeColorClass = "bg-rose-500 border-rose-600";
            }
        }

        return ActionPermissionDto.builder()
                .key(accion.getSCodigo())
                .label(accion.getSNombre() != null ? accion.getSNombre().toUpperCase() : code)
                .colorClass(colorClass)
                .activeColorClass(activeColorClass)
                .build();
    }
}
