package backofficeapi.application.usecase.action;

import backofficeapi.application.port.input.action.CreateTblActionUseCase;
import backofficeapi.application.port.output.TblActionRepositoryPort;
import backofficeapi.domain.model.TblActionModel;
import backofficeapi.domain.util.ResourceActionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
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

@Slf4j
@Service
public class CreateTblActionUseCaseImpl implements CreateTblActionUseCase {

    private final TblActionRepositoryPort tblActionRepositoryPort;

    public CreateTblActionUseCaseImpl(TblActionRepositoryPort tblActionRepositoryPort) {
        this.tblActionRepositoryPort = tblActionRepositoryPort;
    }

    @Override
    @Transactional
    public Map<String, TblActionModel> saveActions(List<ResourceActionUtil.AccionBaseDef> accionesBaseList) {
        Map<String, TblActionModel> actionsMap = new HashMap<>();
        for (ResourceActionUtil.AccionBaseDef def : accionesBaseList) {
            TblActionModel action = tblActionRepositoryPort.findByCode(def.codigo())
                    .map(existing -> {
                        existing.setSNombre(def.nombre());
                        existing.setSDescripcion(def.descripcion());
                        return tblActionRepositoryPort.saveAction(existing);
                    }).orElseGet(() -> {
                        TblActionModel newAction = TblActionModel.builder()
                                .sCodigo(def.codigo())
                                .sNombre(def.nombre())
                                .sDescripcion(def.descripcion())
                                .build();
                        return tblActionRepositoryPort.saveAction(newAction);
                    });
            actionsMap.put(def.codigo(), action);
        }
        return actionsMap;
    }
}
