package backofficeapi.application.usecase.resource;

import backofficeapi.application.port.input.resource.CreateOrUpdateResourceUseCase;
import backofficeapi.application.port.output.TblResourceRepositoryPort;
import backofficeapi.domain.model.TblResourceModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: CreateOrUpdateResourceUseCaseImpl
 *   Descripción: Implementación del caso de uso para crear o actualizar un recurso y asociar sus acciones
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   21.09.2026 | Camila Ledezma | Creación Inicial con TblResourceModel
 *----------------------------------------
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CreateOrUpdateResourceUseCaseImpl implements CreateOrUpdateResourceUseCase {

    private final TblResourceRepositoryPort resourceRepositoryPort;

    @Override
    @Transactional
    public TblResourceModel createOrUpdateResource(
            String name,
            String description,
            String icon,
            TblResourceModel parentResource,
            String[] actionCodes) {

        Long parentId = parentResource != null ? parentResource.getId() : null;
        TblResourceModel resource = resourceRepositoryPort.saveOrUpdateResource(name, description, icon, parentId);
        resourceRepositoryPort.linkResourceActions(resource.getId(), actionCodes);

        return resource;
    }
}
