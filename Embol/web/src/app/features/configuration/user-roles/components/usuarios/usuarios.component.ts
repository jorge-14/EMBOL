import { Component, OnInit, inject, signal } from '@angular/core';
import { USER_ROLES_IMPORTS } from '../../enums/user-roles-imports';
import { UserRolesService } from '../../services/user-roles.service';
import { UserRow } from '../../models/users/user.model';
import { PageMetadata } from '../../../../../shared/models/pagination.model';
import { DataTableColumn, DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';
import { buildUsuariosColumns } from '../../configs/users/usuarios-columns.config';
import { buildUserRowActions } from '../../configs/users/usuarios-actions.config';
import { ROLE_OPTIONS_CONFIG, GROUP_OPTIONS_CONFIG } from '../../configs/users/usuarios-filters.config';
import { HorizontalSelectComponent } from '../../../../../shared/components/horizontal-controls/horizontal-select/horizontal-select.component';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [...USER_ROLES_IMPORTS, HorizontalSelectComponent],
  templateUrl: './usuarios.component.html',
})
export class UsuariosComponent implements OnInit {

  // ── 1. Dependencias ────────────────────────────────────────────────────────
  private service = inject(UserRolesService);

  // ── 2. Estado del servidor ─────────────────────────────────────────────────
  loading = signal(true);
  users   = signal<UserRow[]>([]);
  pageInfo = signal<PageMetadata | null>(null);

  // Filtros individuales
  roleFilter = signal<string | null>(null);
  groupFilter = signal<string | null>(null);

  // Estado de Paginación
  currentPage = signal(0);
  pageSize = signal(20);

  // ── 3. Configuración estática (desde configs/users/) ──────────────────────
  readonly roleOptions = ROLE_OPTIONS_CONFIG;
  readonly groupOptions = GROUP_OPTIONS_CONFIG;
  columns: DataTableColumn<UserRow>[] = buildUsuariosColumns();

  rowActions: DataTableRowAction[] = buildUserRowActions({
    onEdit:       (id) => this.onEdit(id),
    onDeactivate: (id) => this.onDeactivate(id),
    onActivate:   (id) => this.onActivate(id),
  });

  // ── 4. Lifecycle ───────────────────────────────────────────────────────────
  ngOnInit(): void {
    this.loadUsers();
  }

  // ── 5. Carga de datos ──────────────────────────────────────────────────────
  loadUsers(): void {
    this.loading.set(true);
    
    const filters = {
      role: this.roleFilter(), //set en html
      group: this.groupFilter() //set en html
    };

    this.service.getPagedUsers(this.currentPage(), this.pageSize(), filters).subscribe({
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

  // ── 6. Acciones de fila ────────────────────────────────────────────────────
  onEdit(id: any): void       { console.log('[Usuarios] Editar →', id);      /* TODO: dialog */ }
  onDeactivate(id: any): void { console.log('[Usuarios] Desactivar →', id);  /* TODO: confirm */ }
  onActivate(id: any): void   { console.log('[Usuarios] Activar →', id);     /* TODO: confirm */ }
  onAdd(): void               { console.log('[Usuarios] Nuevo usuario');      /* TODO: form    */ }

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
