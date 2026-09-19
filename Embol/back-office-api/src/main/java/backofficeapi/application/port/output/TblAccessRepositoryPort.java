package backofficeapi.application.port.output;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblAccion;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecurso;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecursoAccion;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblAccessRepositoryPort
 *   Descripción: Puerto de salida para acceso a datos de la matriz de accesos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */
public interface TblAccessRepositoryPort {

    List<TblRecurso> getParentResources();

    List<TblRecurso> getChildResources(Long parentId);

    List<TblAccion> getAllActions();

    List<TblRecursoAccion> getSupportedResourceActions();

    List<Long> getGrantedResourceIdsForRole(Long roleId);

    List<Long> getGrantedResourceIdsForGroup(Long groupId);

    String getRoleName(Long roleId);

    String getGroupName(Long groupId);

    void saveRoleResources(Long roleId, List<Long> grantedResourceIds);

    void saveGroupResources(Long groupId, List<Long> grantedResourceIds);
}
