package backofficeapi.application.port.output;

import backofficeapi.domain.model.TblResourceModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblAccion;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecurso;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecursoAccion;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblResourceRepositoryPort
 *   Descripción: Puerto de salida para persistencia y consulta de Recursos, Recurso-Acción y Accesos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial en módulo resource
 *   21.09.2026 | Camila Ledezma | Uso de TblResourceModel
 *----------------------------------------
 */
public interface TblResourceRepositoryPort {

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

    TblResourceModel saveOrUpdateResource(String name, String description, String icon, Long parentResourceId);

    void linkResourceActions(Long resourceId, String[] actionCodes);
}
