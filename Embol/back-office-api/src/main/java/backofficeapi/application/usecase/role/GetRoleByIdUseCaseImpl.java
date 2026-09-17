package backofficeapi.application.usecase.role;

import backofficeapi.application.port.input.role.GetRoleByIdUseCase;
import backofficeapi.application.port.output.TblRoleRepositoryPort;
import backofficeapi.domain.exception.BusinessApiException;
import backofficeapi.domain.model.TblRoleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: GetRoleByIdUseCaseImpl
 *   Descripción: Implementación del caso de uso para obtener un rol por ID
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   17.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Slf4j
@Service
public class GetRoleByIdUseCaseImpl implements GetRoleByIdUseCase {

    private final TblRoleRepositoryPort tblRoleRepositoryPort;

    public GetRoleByIdUseCaseImpl(TblRoleRepositoryPort tblRoleRepositoryPort) {
        this.tblRoleRepositoryPort = tblRoleRepositoryPort;
    }

    @Override
    @Transactional(readOnly = true)
    public TblRoleModel getRoleById(Long id) {
        if (id == null) {
            log.error("Error de validación: el ID del role es requerido");
            throw new BusinessApiException(HttpStatus.BAD_REQUEST, "El ID del role es requerido");
        }

        log.info("Buscando rol con ID: {}", id);
        return tblRoleRepositoryPort.findById(id).orElseThrow(() -> {
            log.error("Error de negocio: no existe el rol con ID: {}", id);
            return new BusinessApiException(HttpStatus.NOT_FOUND, "No se encontro el rol");
        });
    }
}
