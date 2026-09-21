package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblActionRepositoryPort;
import backofficeapi.domain.model.TblActionModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblAccion;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.TblActionMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblActionRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

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


@Component
public class TblActionRepositoryAdapter implements TblActionRepositoryPort {

    private final TblActionRepository tblActionRepository;
    private final TblActionMapper tblActionMapper;

    public TblActionRepositoryAdapter(TblActionRepository tblActionRepository, TblActionMapper tblActionMapper) {
        this.tblActionRepository = tblActionRepository;
        this.tblActionMapper = tblActionMapper;
    }

    @Override
    public Optional<TblActionModel> findByCode(String code) {
        return tblActionRepository.actionFindByCode(code)
                .map(tblActionMapper::toModel);
    }

    @Override
    public TblActionModel saveAction(TblActionModel tblActionModel) {
        TblAccion entity = tblActionMapper.toEntity(tblActionModel);
        TblAccion savedEntity = tblActionRepository.save(entity);
        return tblActionMapper.toModel(savedEntity);
    }
}
