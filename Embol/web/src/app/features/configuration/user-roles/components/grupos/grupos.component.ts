import { Component, OnInit, inject, signal } from '@angular/core';
import { USER_ROLES_IMPORTS } from '../../enums/user-roles-imports';
import { UserRolesService } from '../../services/user-roles.service';
import { GrupoRow } from '../../models/grupos/grupo.model';
import { PageMetadata } from '../../../../../shared/models/pagination.model';
import { DataTableColumn, DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';
import { buildGruposColumns } from '../../configs/grupos/grupos-columns.config';
import { buildGrupoRowActions } from '../../configs/grupos/grupos-actions.config';

@Component({
  selector: 'app-grupos',
  standalone: true,
  imports: [...USER_ROLES_IMPORTS],
  templateUrl: './grupos.component.html',
})
export class GruposComponent implements OnInit {

  // ── 1. Dependencias ────────────────────────────────────────────────────────
  private service = inject(UserRolesService);

  // ── 2. Estado del servidor ─────────────────────────────────────────────────
  loading = signal(true);
  groups  = signal<GrupoRow[]>([]);
  pageInfo = signal<PageMetadata | null>(null);

  private currentPage = 0;
  private pageSize = 20;

  // ── 3. Configuración estática (desde configs/grupos/) ─────────────────────
  columns: DataTableColumn<GrupoRow>[] = buildGruposColumns();

  rowActions: DataTableRowAction[] = buildGrupoRowActions({
    onEdit:   (id) => this.onEdit(id),
    onDelete: (id) => this.onDelete(id),
  });

  // ── 4. Lifecycle ───────────────────────────────────────────────────────────
  ngOnInit(): void {
    this.loadGroups();
  }

  // ── 5. Carga de datos ──────────────────────────────────────────────────────
  loadGroups(page: number = this.currentPage, size: number = this.pageSize): void {
    this.loading.set(true);
    this.service.getPagedGroups(page, size).subscribe({
      next: (result) => {
        this.groups.set(result.content);
        this.pageInfo.set(result.page);
        this.currentPage = page;
        this.pageSize = size;
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error cargando grupos', err);
        this.loading.set(false);
      },
    });
  }

  // ── 6. Acciones de fila ────────────────────────────────────────────────────
  onEdit(id: any): void   { console.log('[Grupos] Editar →', id);   /* TODO: popup centrado */ }
  onDelete(id: any): void { console.log('[Grupos] Eliminar →', id); /* TODO: confirm centrado */ }
  onAdd(): void           { console.log('[Grupos] Nuevo grupo');     /* TODO: popup centrado  */ }

  // ── 7. Paginación ──────────────────────────────────────────────────────────
  onPageChange(page: number): void {
    this.loadGroups(page, this.pageSize);
  }

  onPageSizeChange(size: number): void {
    this.loadGroups(0, size);
  }
}
