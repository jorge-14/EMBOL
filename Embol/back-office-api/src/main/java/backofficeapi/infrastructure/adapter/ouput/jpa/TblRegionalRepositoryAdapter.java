package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.TblRegionalRepositoryPort;
import backofficeapi.domain.model.TblRegionalModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRegional;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.TblRegionalMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblRegionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: TblRegionalRepositoryAdapter
 *   Descripción: Adaptador de persistencia JPA para TblRegional (TBL_REGIONAL)
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Service
@RequiredArgsConstructor
public class TblRegionalRepositoryAdapter implements TblRegionalRepositoryPort {

    private final TblRegionalRepository repository;
    private final TblRegionalMapper mapper;

    @Override
    public TblRegionalModel saveRegional(TblRegionalModel model) {
        TblRegional entity = mapper.toEntity(model);
        TblRegional saved = repository.save(entity);
        return mapper.toModel(saved);
    }

    @Override
    public List<TblRegionalModel> listAllRegionals() {
        return repository.findAll().stream()
                .map(mapper::toModel)
                .toList();
    }

    @Override
    public Optional<TblRegionalModel> getRegionalById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Override
    public Optional<TblRegionalModel> getRegionalByCode(String code) {
        return repository.findByCodeIgnoreCase(code).map(mapper::toModel);
    }

    @Override
    public Page<TblRegionalModel> getListPageRegional(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toModel);
    }
}
