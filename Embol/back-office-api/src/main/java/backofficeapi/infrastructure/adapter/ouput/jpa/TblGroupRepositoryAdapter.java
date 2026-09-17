package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblGroupRepositoryPort;
import backofficeapi.domain.model.TblGroupModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblGrupo;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.TblGroupMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblGroupRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/*
 *----------------------------------------
 *   Código de Aplicación:
 *   Código de Objeto:
 *   Descripción:
 *   Author Prog: Jorge Luis Choque Callizaya
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   16.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Component
public class TblGroupRepositoryAdapter implements TblGroupRepositoryPort {

    private final TblGroupRepository tblGroupRepository;
    private final TblGroupMapper tblGroupMapper;

    public TblGroupRepositoryAdapter(TblGroupRepository tblGroupRepository, TblGroupMapper tblGroupMapper) {
        this.tblGroupRepository = tblGroupRepository;
        this.tblGroupMapper = tblGroupMapper;
    }

    @Override
    @Transactional
    public TblGroupModel saveGroup(TblGroupModel tblGroupModel) {

        TblGrupo tblGrupo = tblGroupMapper.toEntity(tblGroupModel);
        TblGrupo saved = tblGroupRepository.save(tblGrupo);

        return tblGroupMapper.toModel(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TblGroupModel> getPageListGroup(Pageable pageable) {
        Page<TblGrupo> pageListGroup = tblGroupRepository.pageListGroup(pageable);
        return pageListGroup.map(tblGroupMapper::toModel);
    }
}
