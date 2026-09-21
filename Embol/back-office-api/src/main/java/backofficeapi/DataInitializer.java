package backofficeapi;

import backofficeapi.application.port.input.action.CreateTblActionUseCase;
import backofficeapi.domain.model.TblActionModel;
import backofficeapi.domain.util.ResourceActionUtil;
import backofficeapi.domain.util.ResourceActionUtil.AccionBaseDef;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblAccion;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecurso;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblRecursoAccion;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblActionRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblResourceActionRepository;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: DataInitializer
 *   Descripción: Componente de inicialización y sincronización automática de Acciones y Recursos
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial 
 *----------------------------------------
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CreateTblActionUseCase createTblActionUseCase;

    private final TblActionRepository actionRepository;
    private final TblResourceRepository resourceRepository;
    private final TblResourceActionRepository resourceActionRepository;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("***************** [DataInitializer] Iniciando sincronización de Acciones y Recursos *****************");
//        Map<String, TblActionModel> mapActions = addActions();
//        buildDefaultMenu(mapActions);
        log.info("***************** [DataInitializer] Sincronización completada exitosamente *****************");
    }

    private Map<String, TblActionModel> addActions() {
        List<AccionBaseDef> accionesBaseList = ResourceActionUtil.getAccionesBaseList();
        return createTblActionUseCase.saveActions(accionesBaseList);
    }

    // Construye Recursos y se asocia con las acciones

    private void buildDefaultMenu(Map<String, TblAccion> mapActions) {
        // -------------------------------------------------------------
        // 1. MÓDULO PADRE: ADMINISTRACIÓN
        // -------------------------------------------------------------
        TblRecurso modAdmin = createOrUpdateResource(
                "ADMINISTRACIÓN",
                "Módulo de gestión y administración general del sistema",
                "settings",
                null,
                null,
                mapActions);

        createOrUpdateResource(
                "Usuarios",
                "Interfaz para administración y gestión de usuarios",
                "users",
                modAdmin,
                ResourceActionUtil.USER_ACTIONS,
                mapActions);

        createOrUpdateResource(
                "Roles y Grupos",
                "Interfaz para administración de roles y grupos de usuarios",
                "shield",
                modAdmin,
                ResourceActionUtil.ROLE_ACTIONS,
                mapActions);

        createOrUpdateResource(
                "Accesos",
                "Interfaz para configuración y asignación de matriz de accesos",
                "lock",
                modAdmin,
                ResourceActionUtil.ACCESS_ACTIONS,
                mapActions);

        createOrUpdateResource(
                "Parámetros del Sistema",
                "Interfaz para administración de parámetros y configuraciones globales",
                "sliders",
                modAdmin,
                ResourceActionUtil.READ_WRITE_ACTIONS,
                mapActions);

        // -------------------------------------------------------------
        // 2. MÓDULO PADRE: SIMULADOR
        // -------------------------------------------------------------
        TblRecurso modSimulador = createOrUpdateResource(
                "SIMULADOR",
                "Módulo de simulaciones y escenarios salariales",
                "calculator",
                null,
                null,
                mapActions);

        createOrUpdateResource(
                "Simulador Salarial",
                "Interfaz para simulación de costos y cálculos salariales",
                "dollar-sign",
                modSimulador,
                ResourceActionUtil.SIMULATOR_ACTIONS,
                mapActions);

        createOrUpdateResource(
                "Escenarios",
                "Interfaz para gestión y comparación de escenarios salariales",
                "layers",
                modSimulador,
                ResourceActionUtil.CRUD_ACTIONS,
                mapActions);
    }

    // Crea o actualiza un Recurso y vincula sus acciones permitidas
    // enTBL_RECURSO_ACCION
    private TblRecurso createOrUpdateResource(
            String name,
            String description,
            String icon,
            TblRecurso parentResource,
            String[] actionCodes,
            Map<String, TblAccion> mapActions) {

        TblRecurso resource = resourceRepository.findBySNombre(name)
                .map(existing -> {
                    existing.setSDescripcion(description);
                    existing.setIcono(icon);
                    existing.setIIdRecursoPadre(parentResource);
                    return resourceRepository.save(existing);
                })
                .orElseGet(() -> resourceRepository.save(
                        TblRecurso.builder()
                                .sNombre(name)
                                .sDescripcion(description)
                                .icono(icon)
                                .iIdRecursoPadre(parentResource)
                                .build()));

        log.info("[DataInitializer] Recurso sincronizado: {} (Padre: {})",
                resource.getSNombre(),
                parentResource != null ? parentResource.getSNombre() : "NINGUNO");

        // TBL_RECURSO_ACCION
        if (actionCodes != null) {
            for (String code : actionCodes) {
                TblAccion action = mapActions.get(code);
                if (action != null && !resourceActionRepository.existsByIIdRecursoAndIIdAccion(resource, action)) {
                    resourceActionRepository.save(
                            TblRecursoAccion.builder()
                                    .iIdRecurso(resource)
                                    .iIdAccion(action)
                                    .build());
                    log.info("  -> Acción vinculada al recurso [{}]: {}", resource.getSNombre(), action.getSCodigo());
                }
            }
        }

        return resource;
    }
}
