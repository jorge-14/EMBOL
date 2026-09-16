import { Component, OnInit, inject, signal } from '@angular/core';
import { USER_ROLES_IMPORTS } from '../../enums/user-roles-imports';
import { UserRolesService } from '../../services/user-roles.service';
import { UserRow } from '../../models/users/user.model';
import { PageMetadata } from '../../../../../shared/models/pagination.model';
import { DataTableColumn, DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';
import { buildUsuariosColumns } from '../../configs/users/usuarios-columns.config';
import { buildUserRowActions } from '../../configs/users/usuarios-actions.config';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [...USER_ROLES_IMPORTS],
  templateUrl: './usuarios.component.html',
})
export class UsuariosComponent implements OnInit {

  // ── 1. Dependencias ────────────────────────────────────────────────────────
  private service = inject(UserRolesService);

  // ── 2. Estado del servidor ─────────────────────────────────────────────────
  loading = signal(true);
  users   = signal<UserRow[]>([]);
  pageInfo = signal<PageMetadata | null>(null);

  private currentPage = 0;
  private pageSize = 20;

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
  }

  // ── 5. Carga de datos ──────────────────────────────────────────────────────
  loadUsers(page: number = this.currentPage, size: number = this.pageSize): void {
    this.loading.set(true);
    this.service.getPagedUsers(page, size).subscribe({
      next: (result) => {
        this.users.set(result.content);
        this.pageInfo.set(result.page);
        this.currentPage = page;
        this.pageSize = size;
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
    this.loadUsers(page, this.pageSize);
  }

  onPageSizeChange(size: number): void {
    this.loadUsers(0, size);
  }
}
