package backofficeapi.domain.util;

import java.util.List;

/*
 *----------------------------------------
 *   Código de Aplicación: EMBOL
 *   Código de Objeto: ResourceActionUtil
 *   Descripción: Constantes y utilitarios para códigos de acciones y recursos del sistema
 *   Author Prog: Camila Ledezma
 *----------------------------------------
 *   Fecha | Autor | Comentario
 *   18.09.2026 | Camila Ledezma | Creación Inicial con 4 acciones básicas (VER, CREAR, MODIFICAR, ELIMINAR)
 *----------------------------------------
 */
public final class ResourceActionUtil {

    private ResourceActionUtil() {

    }

    public static final String ACTION_VER = "VER";
    public static final String ACTION_CREAR = "CREAR";
    public static final String ACTION_MODIFICAR = "MODIFICAR";
    public static final String ACTION_ELIMINAR = "ELIMINAR";

    public static final String[] CRUD_ACTIONS = new String[] {
            ACTION_VER, ACTION_CREAR, ACTION_MODIFICAR, ACTION_ELIMINAR
    };

    public static final String[] READ_ONLY_ACTIONS = new String[] {
            ACTION_VER
    };

    public static final String[] READ_WRITE_ACTIONS = new String[] {
            ACTION_VER, ACTION_MODIFICAR
    };

    public static final String[] USER_ACTIONS = CRUD_ACTIONS;
    public static final String[] ROLE_ACTIONS = CRUD_ACTIONS;
    public static final String[] GROUP_ACTIONS = CRUD_ACTIONS;
    public static final String[] ACCESS_ACTIONS = READ_WRITE_ACTIONS;
    public static final String[] SIMULATOR_ACTIONS = CRUD_ACTIONS;

    public record AccionBaseDef(String codigo, String nombre, String descripcion) {
    }

    public static List<AccionBaseDef> getAccionesBaseList() {
        return List.of(
                new AccionBaseDef(ACTION_VER, "Ver", "Permite visualizar y listar registros"),
                new AccionBaseDef(ACTION_CREAR, "Crear", "Permite registrar nuevos elementos"),
                new AccionBaseDef(ACTION_MODIFICAR, "Modificar", "Permite editar o actualizar elementos existentes"),
                new AccionBaseDef(ACTION_ELIMINAR, "Eliminar", "Permite eliminar o inactivar elementos"));
    }
}
