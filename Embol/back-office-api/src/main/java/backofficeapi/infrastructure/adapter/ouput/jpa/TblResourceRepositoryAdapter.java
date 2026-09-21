package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblResourceRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.model.TblResourceModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.*;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblResourceRepositoryAdapter
 *   Descripción: Adaptador JPA para operaciones de Recursos, Recurso-Acción y Accesos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial en módulo resource
 *----------------------------------------
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TblResourceRepositoryAdapter implements TblResourceRepositoryPort {

    private final TblResourceRepository resourceRepository;
    private final TblActionRepository actionRepository;
    private final TblResourceActionRepository resourceActionRepository;
    private final TblRoleRepository roleRepository;
    private final TblGroupRepository groupRepository;
    private final TblRoleResourceRepository roleResourceRepository;
    private final TblGroupResourceRepository groupResourceRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TblRecurso> getParentResources() {
        return resourceRepository.findAllParents();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblRecurso> getChildResources(Long parentId) {
        return resourceRepository.findByPadreId(parentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblAccion> getAllActions() {
        return actionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TblRecursoAccion> getSupportedResourceActions() {
        return resourceActionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getGrantedResourceIdsForRole(Long roleId) {
        return roleResourceRepository.findGrantedResourceIdsByRolId(roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getGrantedResourceIdsForGroup(Long groupId) {
        return groupResourceRepository.findGrantedResourceIdsByGrupoId(groupId);
    }

    @Override
    @Transactional(readOnly = true)
    public String getRoleName(Long roleId) {
        return roleRepository.findActiveById(roleId)
                .map(TblRol::getSNombre)
                .orElseThrow(
                        () -> new BusinessApiException(HttpStatus.NOT_FOUND, "Rol no encontrado con ID: " + roleId));
    }

    @Override
    @Transactional(readOnly = true)
    public String getGroupName(Long groupId) {
        return groupRepository.findById(groupId)
                .map(TblGrupo::getSNombre)
                .orElseThrow(
                        () -> new BusinessApiException(HttpStatus.NOT_FOUND, "Grupo no encontrado con ID: " + groupId));
    }

    @Override
    @Transactional
    public void saveRoleResources(Long roleId, List<Long> grantedResourceIds) {
        TblRol rol = roleRepository.findActiveById(roleId)
                .orElseThrow(
                        () -> new BusinessApiException(HttpStatus.NOT_FOUND, "Rol no encontrado con ID: " + roleId));

        roleResourceRepository.softDeleteAllByRolId(roleId);

        if (grantedResourceIds != null) {
            for (Long resourceId : grantedResourceIds) {
                TblRecurso recurso = resourceRepository.findById(resourceId).orElse(null);
                if (recurso != null) {
                    roleResourceRepository.findByRolIdAndRecursoId(roleId, resourceId)
                            .ifPresentOrElse(
                                    existing -> {
                                        existing.setDeleted(false);
                                        roleResourceRepository.save(existing);
                                    },
                                    () -> {
                                        TblRolRecurso nuevo = TblRolRecurso.builder()
                                                .iIdRol(rol)
                                                .iIdRecurso(recurso)
                                                .build();
                                        nuevo.setDeleted(false);
                                        roleResourceRepository.save(nuevo);
                                    });
                }
            }
        }
    }

    @Override
    @Transactional
    public void saveGroupResources(Long groupId, List<Long> grantedResourceIds) {
        TblGrupo grupo = groupRepository.findById(groupId)
                .orElseThrow(
                        () -> new BusinessApiException(HttpStatus.NOT_FOUND, "Grupo no encontrado con ID: " + groupId));

        groupResourceRepository.softDeleteAllByGrupoId(groupId);

        if (grantedResourceIds != null) {
            for (Long resourceId : grantedResourceIds) {
                TblRecurso recurso = resourceRepository.findById(resourceId).orElse(null);
                if (recurso != null) {
                    groupResourceRepository.findByGrupoIdAndRecursoId(groupId, resourceId)
                            .ifPresentOrElse(
                                    existing -> {
                                        existing.setDeleted(false);
                                        groupResourceRepository.save(existing);
                                    },
                                    () -> {
                                        TblGrupoRecurso nuevo = TblGrupoRecurso.builder()
                                                .iIdeGrupo(grupo)
                                                .iIdRecurso(recurso)
                                                .build();
                                        nuevo.setDeleted(false);
                                        groupResourceRepository.save(nuevo);
                                    });
                }
            }
        }
    }

    @Override
    @Transactional
    public TblResourceModel saveOrUpdateResource(String name, String description, String icon, Long parentResourceId) {
        TblRecurso parentEntity = parentResourceId != null ? resourceRepository.findById(parentResourceId).orElse(null)
                : null;

        TblRecurso entity = resourceRepository.findBySNombre(name)
                .map(existing -> {
                    existing.setSDescripcion(description);
                    existing.setIcono(icon);
                    existing.setIIdRecursoPadre(parentEntity);
                    return resourceRepository.save(existing);
                })
                .orElseGet(() -> resourceRepository.save(
                        TblRecurso.builder()
                                .sNombre(name)
                                .sDescripcion(description)
                                .icono(icon)
                                .iIdRecursoPadre(parentEntity)
                                .build()));

        return TblResourceModel.builder()
                .id(entity.getIIdRecurso())
                .name(entity.getSNombre())
                .description(entity.getSDescripcion())
                .icon(entity.getIcono())
                .parentResourceId(parentEntity != null ? parentEntity.getIIdRecurso() : null)
                .deleted(entity.isDeleted())
                .createdDate(entity.getCreatedDate())
                .createdBy(entity.getCreatedBy())
                .modifiedDate(entity.getModifiedDate())
                .modifiedBy(entity.getModifiedBy())
                .build();
    }

    @Override
    @Transactional
    public void linkResourceActions(Long resourceId, String[] actionCodes) {
        if (actionCodes != null && resourceId != null) {
            TblRecurso resource = resourceRepository.findById(resourceId).orElse(null);
            if (resource != null) {
                for (String code : actionCodes) {
                    actionRepository.findBySCodigo(code).ifPresent(action -> {
                        if (!resourceActionRepository.existsByIIdRecursoAndIIdAccion(resource, action)) {
                            resourceActionRepository.save(
                                    TblRecursoAccion.builder()
                                            .iIdRecurso(resource)
                                            .iIdAccion(action)
                                            .build());
                        }
                    });
                }
            }
        }
    }
}
