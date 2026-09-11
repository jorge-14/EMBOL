package backofficeapi.infrastructure.adapter.ouput.jpa;

import backofficeapi.application.port.output.AuthRoleRepositoryPort;
import backofficeapi.domain.model.AuthRoleModel;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthRole;
import backofficeapi.infrastructure.adapter.ouput.jpa.mapper.AuthRoleMapper;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.AuthRoleRepository;
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
 *   11.09.2026 | Jorge Luis Choque Callizaya | Creación Inicial
 *----------------------------------------
 */

@Component
public class AuthRoleRepositoryAdapter implements AuthRoleRepositoryPort {

    private final AuthRoleRepository authRoleRepository;
    private final AuthRoleMapper authRoleMapper;

    public AuthRoleRepositoryAdapter(AuthRoleRepository authRoleRepository, AuthRoleMapper authRoleMapper) {
        this.authRoleRepository = authRoleRepository;
        this.authRoleMapper = authRoleMapper;
    }

    @Override
    @Transactional
    public AuthRoleModel saveRole(AuthRoleModel authRoleModel) {
        AuthRole authRole = authRoleMapper.toEntity(authRoleModel);
        AuthRole authRoleSave = authRoleRepository.save(authRole);
        return authRoleMapper.toModel(authRoleSave);
    }
}
