package backofficeapi.infrastructure.adapter.ouput.jpa.repository;

import backofficeapi.infrastructure.adapter.ouput.jpa.entity.AuthRegional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: AuthRegionalRepository
 *   Descripción: Repositorio JPA para operaciones en tabla AUTH_REGIONAL
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   11.09.2026 | Camila Ledezma | Creación Inicial
 *----------------------------------------
 */

@Repository
public interface AuthRegionalRepository extends JpaRepository<AuthRegional, Long> {

    Optional<AuthRegional> findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);
}
