package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblAccessRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.*;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblAccessRepositoryAdapter
 *   Descripción: Adaptador JPA que implementa TblAccessRepositoryPort para la matriz de accesos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
@Component
@RequiredArgsConstructor
public class TblAccessRepositoryAdapter implements TblAccessRepositoryPort {

    private final TblResourceRepository resourceRepository;
    private final TblActionRepository actionRepository;
    private final TblResourceActionRepository resourceActionRepository;
    private final TblRoleResourceRepository roleResourceRepository;
    private final TblGroupResourceRepository groupResourceRepository;
    private final TblRoleRepository roleRepository;
    private final TblGroupRepository groupRepository;

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
                resourceRepository.findById(resourceId).ifPresent(recurso -> {
                    roleResourceRepository.findByRolIdAndRecursoId(roleId, resourceId)
                            .map(existing -> {
                                existing.setDeleted(false);
                                return roleResourceRepository.save(existing);
                            })
                            .orElseGet(() -> roleResourceRepository.save(
                                    TblRolRecurso.builder()
                                            .iIdRol(rol)
                                            .iIdRecurso(recurso)
                                            .build()));
                });
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
                resourceRepository.findById(resourceId).ifPresent(recurso -> {
                    groupResourceRepository.findByGrupoIdAndRecursoId(groupId, resourceId)
                            .map(existing -> {
                                existing.setDeleted(false);
                                return groupResourceRepository.save(existing);
                            })
                            .orElseGet(() -> groupResourceRepository.save(
                                    TblGrupoRecurso.builder()
                                            .iIdeGrupo(grupo)
                                            .iIdRecurso(recurso)
                                            .build()));
                });
            }
        }
    }
}
