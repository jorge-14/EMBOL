package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.AuthRegionalRepositoryPort;
import backofficeapi.domain.model.AuthRegionalModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthRegional;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.AuthRegionalMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.AuthRegionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthRegionalRepositoryAdapter
 *   Descripción: Adaptador JPA de salida para persistencia de AuthRegional
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthRegionalRepositoryAdapter implements AuthRegionalRepositoryPort {

    private final AuthRegionalRepository authRegionalRepository;
    private final AuthRegionalMapper authRegionalMapper;

    @Override
    public AuthRegionalModel saveRegional(AuthRegionalModel model) {
        AuthRegional entity = authRegionalMapper.toEntity(model);
        AuthRegional saved = authRegionalRepository.save(entity);
        return authRegionalMapper.toModel(saved);
    }

    @Override
    public List<AuthRegionalModel> listAllRegionals() {
        return authRegionalRepository.findAll()
                .stream()
                .map(authRegionalMapper::toModel)
                .toList();
    }

    @Override
    public Optional<AuthRegionalModel> getRegionalById(Long id) {
        return authRegionalRepository.findById(id)
                .map(authRegionalMapper::toModel);
    }

    @Override
    public Optional<AuthRegionalModel> getRegionalByCode(String code) {
        return authRegionalRepository.findByCodeIgnoreCase(code)
                .map(authRegionalMapper::toModel);
    }

    @Override
    public void deleteRegionalById(Long id) {
        authRegionalRepository.deleteById(id);
    }

    @Override
    public Page<AuthRegionalModel> getListPageRegional(Pageable pageable) {
        Page<AuthRegional> entityPage = authRegionalRepository.findAll(pageable);
        return entityPage.map(authRegionalMapper::toModel);
    }
}
