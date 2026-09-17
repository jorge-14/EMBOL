import { Component, OnInit, inject, signal } from '@angular/core';
import { GRUPOS_IMPORTS } from '../../enums/user-roles-imports';
import { UserRolesService } from '../../services/user-roles.service';
import { GrupoRow } from '../../models/grupos/grupo.model';
import { DataTableColumn, DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';
import { buildGruposColumns } from '../../configs/grupos/grupos-columns.config';
import { buildGrupoRowActions } from '../../configs/grupos/grupos-actions.config';
import { buildNuevoGrupoConfig } from '../../configs/grupos/grupos-form.config';
import { DynamicFormConfig } from '../../../../../shared/components/dynamic-form/models/dynamic-form.model';
import { PageMetadata } from '../../../../../shared/models/pagination.model';

@Component({
  selector: 'app-grupos',
  standalone: true,
  imports: [...GRUPOS_IMPORTS],
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

  isNewGroupModalOpen = signal(false);
  newGroupModalConfig = signal<DynamicFormConfig | null>(null);

  // ── 6. Acciones de fila ────────────────────────────────────────────────────
  onEdit(id: any): void       { console.log('[Grupos] Editar →', id);      /* TODO: form */ }
  onDeactivate(id: any): void { console.log('[Grupos] Desactivar →', id);  /* TODO: confirm */ }
  onDelete(id: any): void     { this.onDeactivate(id); }
  
  onAdd(): void {
    this.newGroupModalConfig.set(buildNuevoGrupoConfig());
    this.isNewGroupModalOpen.set(true);
  }

  onNewGroupSubmit(data: any): void {
    console.log('[Grupos] Guardar →', data);
    this.isNewGroupModalOpen.set(false);
    this.loadGroups();
  }

  onNewGroupCancel(): void {
    this.isNewGroupModalOpen.set(false);
  }

  // ── 7. Paginación ──────────────────────────────────────────────────────────
  onPageChange(page: number): void {
    this.loadGroups(page, this.pageSize);
  }

  onPageSizeChange(size: number): void {
    this.loadGroups(0, size);
  }
}
