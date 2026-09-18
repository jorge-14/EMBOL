import { Component, OnInit, inject, signal } from '@angular/core';
import { USUARIOS_IMPORTS } from '../../enums/user-roles-imports';
import { UserService } from '../../../../../core/services/user.service';
import { RoleService } from '../../../../../core/services/role.service';
import { GroupService } from '../../../../../core/services/group.service';
import { RolRow } from '../../models/roles/rol.model';
import { GrupoRow } from '../../models/grupos/grupo.model';
import { UserRow } from '../../models/users/user.model';
import { PageMetadata } from '../../../../../shared/models/pagination.model';
import { DataTableColumn, DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';
import { buildUsuariosColumns } from '../../configs/users/usuarios-columns.config';
import { buildUserRowActions } from '../../configs/users/usuarios-actions.config';
import { buildUsuarioConfig } from '../../configs/users/usuarios-form.config';
import { DynamicFormConfig } from '../../../../../shared/components/dynamic-form/models/dynamic-form.model';
import { ConfirmModalConfig } from '../../../../../shared/components/confirm-modal/models/confirm-modal.model';
import { SelectOption } from '../../../../../shared/components/horizontal-controls/models/select-option.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [...USUARIOS_IMPORTS],
  templateUrl: './usuarios.component.html',
})
export class UsuariosComponent implements OnInit {

  // ── 1. Dependencias ────────────────────────────────────────────────────────
  private usersService = inject(UserService);
  private rolesService = inject(RoleService);
  private groupsService = inject(GroupService);

  // ── 2. Estado del servidor ─────────────────────────────────────────────────
  loading = signal(true);
  users   = signal<UserRow[]>([]);
  pageInfo = signal<PageMetadata | null>(null);

  // Filtros individuales
  roleFilter = signal<string | null>(null);
  groupFilter = signal<string | null>(null);

  // Opciones dinámicas para filtros
  roleOptions = signal<SelectOption[]>([]);
  groupOptions = signal<SelectOption[]>([]);

  // Estado de Paginación
  currentPage = signal(0);
  pageSize = signal(20);

  // ── 3. Configuración estática (desde configs/users/) ──────────────────────
  columns: DataTableColumn<UserRow>[] = buildUsuariosColumns();

  rowActions: DataTableRowAction[] = buildUserRowActions({
    onEdit:       (id) => this.onEdit(id),
    onDeactivate: (id) => this.onDeactivate(id),
    onActivate:   (id) => this.onActivate(id),
  });

  // ── 4. Lifecycle ───────────────────────────────────────────────────────────
  ngOnInit(): void {
    this.loadUsers();
    this.loadFilterOptions();
  }

  loadFilterOptions(): void {
    forkJoin({
      roles: this.rolesService.getRoleList(),
      groups: this.groupsService.getGroupList()
    }).subscribe({
      next: ({ roles, groups }) => {
        this.roleOptions.set(roles.map(r => ({ value: r.nombre, label: r.nombre })));
        this.groupOptions.set(groups.map(g => ({ value: g.nombre, label: g.nombre })));
      },
      error: (err) => console.error('Error cargando opciones de filtro', err)
    });
  }

  // ── 5. Carga de datos ──────────────────────────────────────────────────────
  loadUsers(): void {
    this.loading.set(true);

    const filters = {
      role: this.roleFilter(), //set en html
      group: this.groupFilter() //set en html
    };

    this.usersService.getPagedUsers(this.currentPage(), this.pageSize(), filters).subscribe({
      next: (result) => {
        this.users.set(result.content);
        this.pageInfo.set(result.page);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error cargando usuarios', err);
        this.loading.set(false);
      },
    });
  }

  isUserModalOpen = signal(false);
  userModalConfig = signal<DynamicFormConfig | null>(null);
  userModalData = signal<any>(null);

  // Modal de Confirmación para desactivar/eliminar
  isConfirmModalOpen = signal(false);
  confirmModalConfig = signal<ConfirmModalConfig>({
    title: '',
    description: ''
  });
  private userToDeactivateId: any = null;

  // ── 6. Acciones de fila ────────────────────────────────────────────────────
  onEdit(id: any): void {
    this.loading.set(true);
    forkJoin({
      user: this.usersService.getUserById(id),
      roles: this.rolesService.getRoleList(),
      groups: this.groupsService.getGroupList()
    }).subscribe({
      next: ({ user, roles, groups }) => {
        this.loading.set(false);
        if (!user) return;

        this.userModalConfig.set(buildUsuarioConfig(roles, groups, true));

        // Mapeamos los nombres de roles/grupos del usuario a sus IDs correspondientes
        const userRolesIds = roles.filter(r => user.roles.includes(r.nombre)).map(r => r.id);
        const userGroupIds = groups.filter(g => user.grupos.includes(g.nombre)).map(g => g.id);

        this.userModalData.set({
          id: user.id,
          nombre: user.nombreCompleto,
          email: user.correo,
          estado: user.estado,
          roles: userRolesIds,
          grupos: userGroupIds
        });

        this.isUserModalOpen.set(true);
      },
      error: (err) => {
        console.error('Error al obtener datos para editar usuario', err);
        this.loading.set(false);
      }
    });
  }

  onDeactivate(id: any): void {
    this.userToDeactivateId = id;
    this.confirmModalConfig.set({
      title: 'Desactivar Usuario',
      description: '¿Está seguro de que desea desactivar este usuario? Esta acción limitará su acceso al sistema.',
      confirmLabel: 'Desactivar',
      cancelLabel: 'Cancelar',
      icon: 'danger'
    });
    this.isConfirmModalOpen.set(true);
  }

  onConfirmDeactivate(): void {
    if (this.userToDeactivateId) {
      this.loading.set(true);
      this.usersService.deleteUser(this.userToDeactivateId).subscribe({
        next: () => {
          this.isConfirmModalOpen.set(false);
          this.loadUsers();
        },
        error: (err) => {
          console.error('Error al desactivar usuario', err);
          this.loading.set(false);
          this.isConfirmModalOpen.set(false);
        }
      });
    }
  }

  onConfirmCancel(): void {
    this.isConfirmModalOpen.set(false);
    this.userToDeactivateId = null;
  }

  onActivate(id: any): void   {
    console.log('[Usuarios] Activar →', id);
    // Implementar si existe el endpoint en UserService
  }

  onAdd(): void {
    this.loading.set(true);
    forkJoin({
      roles: this.rolesService.getRoleList(),
      groups: this.groupsService.getGroupList()
    }).subscribe({
      next: ({ roles, groups }) => {
        this.loading.set(false);
        this.userModalConfig.set(buildUsuarioConfig(roles, groups, false));
        this.userModalData.set(null);
        this.isUserModalOpen.set(true);
      },
      error: (err) => {
        console.error('Error al cargar opciones para nuevo usuario', err);
        this.loading.set(false);
      }
    });
  }

  onUserFormSubmit(data: any): void {
    const isEdit = !!this.userModalData();
    if (isEdit) {
      this.onUpdateSubmit(data);
    } else {
      this.onCreateSubmit(data);
    }
  }

  private onUpdateSubmit(data: any): void {
    this.loading.set(true);
    this.usersService.updateUser(this.userModalData().id, data).subscribe({
      next: () => {
        this.isUserModalOpen.set(false);
        this.loadUsers();
      },
      error: (err) => {
        console.error('Error actualizando usuario', err);
        this.loading.set(false);
      }
    });
  }

  private onCreateSubmit(data: any): void {
    this.loading.set(true);
    this.usersService.createUser(data).subscribe({
      next: () => {
        this.isUserModalOpen.set(false);
        this.loadUsers();
      },
      error: (err) => {
        console.error('Error creando usuario', err);
        this.loading.set(false);
      }
    });
  }

  onUserFormCancel(): void {
    this.isUserModalOpen.set(false);
  }

  // ── 7. Paginación ──────────────────────────────────────────────────────────
  onPageChange(page: number): void {
    this.currentPage.set(page);
    this.loadUsers();
  }

  onPageSizeChange(size: number): void {
    this.pageSize.set(size);
    this.currentPage.set(0); // Al cambiar tamaño, volvemos a la página 0
    this.loadUsers();
  }

  // ── 8. Filtros Especiales ──────────────────────────────────────────────────
  onSearch(): void {
    this.currentPage.set(0); // Al buscar, volvemos a la página 0
    this.loadUsers();
  }

  onClear(): void {
    this.roleFilter.set(null);
    this.groupFilter.set(null);
    this.currentPage.set(0); // Al limpiar, volvemos a la página 0
    this.loadUsers();
  }
}
