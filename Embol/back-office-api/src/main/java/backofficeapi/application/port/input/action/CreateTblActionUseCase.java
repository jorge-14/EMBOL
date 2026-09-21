package backofficeapi.application.port.input.action;

import backofficeapi.domain.model.TblActionModel;
import backofficeapi.domain.util.ResourceActionUtil;
import java.util.List;
import java.util.Map;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */
public interface CreateTblActionUseCase {

    Map<String, TblActionModel> saveActions(List<ResourceActionUtil.AccionBaseDef> accionesBaseList);
}
