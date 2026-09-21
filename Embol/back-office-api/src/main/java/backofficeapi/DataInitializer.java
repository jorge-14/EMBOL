package backofficeapi;

import backofficeapi.application.port.input.resource.CreateOrUpdateResourceUseCase;
import backofficeapi.domain.model.TblResourceModel;
import backofficeapi.domain.util.ResourceActionUtil;
import backofficeapi.domain.util.ResourceActionUtil.AccionBaseDef;
import backofficeapi.infrastructure.adapter.ouput.jpa.entity.TblAccion;
import backofficeapi.infrastructure.adapter.ouput.jpa.repository.TblActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: DataInitializer
 *   Descripción: Componente de inicialización de Acciones, Recursos y Recurso-Acción
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial
 *   21.09.2026 | Camila Ledezma | Uso de TblResourceModel y desacoplamiento de TblRecurso
 *----------------------------------------
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TblActionRepository actionRepository;
    private final CreateOrUpdateResourceUseCase createOrUpdateResourceUseCase;

    @Override
    public void run(String... args) {
        log.info("Iniciando inicialización de datos...");
        Map<String, TblAccion> mapActions = addActions();
        buildDefaultMenu(mapActions);
        log.info("Inicialización de datos completada exitosamente.");
    }

    // Inicializa acciones en la tabla tblAccion
    private Map<String, TblAccion> addActions() {
        Map<String, TblAccion> actionsMap = new HashMap<>();

        for (AccionBaseDef def : ResourceActionUtil.getAccionesBaseList()) {
            TblAccion action = actionRepository.findBySCodigo(def.codigo())
                    .map(existing -> {
                        existing.setSNombre(def.nombre());
                        existing.setSDescripcion(def.descripcion());
                        return actionRepository.save(existing);
                    })
                    .orElseGet(() -> actionRepository.save(
                            TblAccion.builder()
                                    .sCodigo(def.codigo())
                                    .sNombre(def.nombre())
                                    .sDescripcion(def.descripcion())
                                    .build()));

            actionsMap.put(def.codigo(), action);
            log.info("[DataInitializer] Acción sincronizada: {} ({})", action.getSNombre(), action.getSCodigo());
        }

        return actionsMap;
    }

    private void buildDefaultMenu(Map<String, TblAccion> mapActions) {
        // -------------------------------------------------------------
        // 1. MÓDULO PADRE: ADMINISTRACIÓN
        // -------------------------------------------------------------
        TblResourceModel modAdmin = createOrUpdateResource(
                "ADMINISTRACIÓN",
                "Módulo de gestión y administración general del sistema",
                "settings",
                null,
                null);

        createOrUpdateResource(
                "Usuarios",
                "Interfaz para administración y gestión de usuarios",
                "users",
                modAdmin,
                ResourceActionUtil.USER_ACTIONS);

        createOrUpdateResource(
                "Roles y Grupos",
                "Interfaz para administración de roles y grupos de usuarios",
                "shield",
                modAdmin,
                ResourceActionUtil.ROLE_ACTIONS);

        createOrUpdateResource(
                "Accesos",
                "Interfaz para configuración y asignación de matriz de accesos",
                "lock",
                modAdmin,
                ResourceActionUtil.ACCESS_ACTIONS);

        createOrUpdateResource(
                "Parámetros del Sistema",
                "Interfaz para administración de parámetros y configuraciones globales",
                "sliders",
                modAdmin,
                ResourceActionUtil.READ_WRITE_ACTIONS);

        // -------------------------------------------------------------
        // 2. MÓDULO PADRE: SIMULADOR
        // -------------------------------------------------------------
        TblResourceModel modSimulador = createOrUpdateResource(
                "SIMULADOR",
                "Módulo de simulaciones y escenarios salariales",
                "calculator",
                null,
                null);

        createOrUpdateResource(
                "Simulador Salarial",
                "Interfaz para simulación de costos y cálculos salariales",
                "dollar-sign",
                modSimulador,
                ResourceActionUtil.SIMULATOR_ACTIONS);

        createOrUpdateResource(
                "Escenarios",
                "Interfaz para gestión y comparación de escenarios salariales",
                "layers",
                modSimulador,
                ResourceActionUtil.CRUD_ACTIONS);
    }

    private TblResourceModel createOrUpdateResource(
            String name,
            String description,
            String icon,
            TblResourceModel parentResource,
            String[] actionCodes) {

        return createOrUpdateResourceUseCase.createOrUpdateResource(name, description, icon, parentResource, actionCodes);
    }
}
