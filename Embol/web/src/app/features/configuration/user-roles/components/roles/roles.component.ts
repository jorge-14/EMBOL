import { Component, OnInit, inject, signal } from '@angular/core';
import { USER_ROLES_IMPORTS } from '../../enums/user-roles-imports';
import { UserRolesService } from '../../services/user-roles.service';
import { RolRow } from '../../models/roles/rol.model';
import { PageMetadata } from '../../../../../shared/models/pagination.model';
import { DataTableColumn, DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';
import { buildRolesColumns } from '../../configs/roles/roles-columns.config';
import { buildRolRowActions } from '../../configs/roles/roles-actions.config';

@Component({
  selector: 'app-roles',
  standalone: true,
  imports: [...USER_ROLES_IMPORTS],
  templateUrl: './roles.component.html',
})
export class RolesComponent implements OnInit {

  // ── 1. Dependencias ────────────────────────────────────────────────────────
  private service = inject(UserRolesService);

  // ── 2. Estado del servidor ─────────────────────────────────────────────────
  loading = signal(true);
  roles   = signal<RolRow[]>([]);
  pageInfo = signal<PageMetadata | null>(null);

  private currentPage = 0;
  private pageSize = 20;

  // ── 3. Configuración estática (desde configs/roles/) ──────────────────────
  columns: DataTableColumn<RolRow>[] = buildRolesColumns();

  rowActions: DataTableRowAction[] = buildRolRowActions({
    onEdit:   (id) => this.onEdit(id),
    onDelete: (id) => this.onDelete(id),
  });

  // ── 4. Lifecycle ───────────────────────────────────────────────────────────
  ngOnInit(): void {
    this.loadRoles();
  }

  // ── 5. Carga de datos ──────────────────────────────────────────────────────
  loadRoles(page: number = this.currentPage, size: number = this.pageSize): void {
    this.loading.set(true);
    this.service.getPagedRoles(page, size).subscribe({
      next: (result) => {
        this.roles.set(result.content);
        this.pageInfo.set(result.page);
        this.currentPage = page;
        this.pageSize = size;
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error cargando roles', err);
        this.loading.set(false);
      },
    });
  }

  // ── 6. Acciones de fila ────────────────────────────────────────────────────
  onEdit(id: any): void   { console.log('[Roles] Editar →', id);   /* TODO: popup centrado */ }
  onDelete(id: any): void { console.log('[Roles] Eliminar →', id); /* TODO: confirm centrado */ }
  onAdd(): void           { console.log('[Roles] Nuevo rol');       /* TODO: popup centrado  */ }

  // ── 7. Paginación ──────────────────────────────────────────────────────────
  onPageChange(page: number): void {
    this.loadRoles(page, this.pageSize);
  }

  onPageSizeChange(size: number): void {
    this.loadRoles(0, size);
  }
}
