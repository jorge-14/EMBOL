import { Component, OnInit, inject, signal } from '@angular/core';
import { GRUPOS_IMPORTS } from '../../enums/user-roles-imports';
import { GroupService } from '../../../../../core/services/group.service';
import { GrupoRow } from '../../models/grupos/grupo.model';
import { DataTableColumn, DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';
import { buildGruposColumns } from '../../configs/grupos/grupos-columns.config';
import { buildGrupoRowActions } from '../../configs/grupos/grupos-actions.config';
import { buildCreateGroupConfig, buildUpdateGroupConfig } from '../../configs/grupos/grupos-form.config';
import { DynamicFormConfig } from '../../../../../shared/components/dynamic-form/models/dynamic-form.model';
import { PageMetadata } from '../../../../../shared/models/pagination.model';
import { ConfirmModalConfig } from '../../../../../shared/components/confirm-modal/models/confirm-modal.model';

@Component({
  selector: 'app-grupos',
  standalone: true,
  imports: [...GRUPOS_IMPORTS],
  templateUrl: './grupos.component.html',
})
export class GruposComponent implements OnInit {

  // ── 1. Dependencias ────────────────────────────────────────────────────────
  private service = inject(GroupService);

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
        console.log("Resultado es ", result);
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

  isGroupModalOpen = signal(false);
  groupModalConfig = signal<DynamicFormConfig | null>(null);
  groupModalData = signal<GrupoRow | null>(null);

  // Modal de Confirmación para eliminar
  isConfirmModalOpen = signal(false);
  confirmModalConfig = signal<ConfirmModalConfig>({
    title: '',
    description: ''
  });
  private groupToDeleteId: any = null;

  // ── 6. Acciones de fila ────────────────────────────────────────────────────
  onEdit(id: any): void {
    this.loading.set(true);
    this.service.getGroupById(id).subscribe({
      next: (group) => {
        this.loading.set(false);
        if (!group) return;

        this.groupModalConfig.set(buildUpdateGroupConfig());
        this.groupModalData.set({
          id: group.id,
          nombre: group.nombre,
          descripcion: group.descripcion,
          estado: group.estado
        });
        this.isGroupModalOpen.set(true);
      },
      error: (err) => {
        console.error('Error obteniendo grupo por ID', err);
        this.loading.set(false);
      }
    });
  }

  onDelete(id: any): void {
    this.groupToDeleteId = id;
    this.confirmModalConfig.set({
      title: 'Eliminar Grupo',
      description: '¿Está seguro de que desea eliminar este grupo? Esta acción no se puede deshacer.',
      confirmLabel: 'Eliminar',
      cancelLabel: 'Cancelar',
      icon: 'danger'
    });
    this.isConfirmModalOpen.set(true);
  }

  onConfirmDelete(): void {
    if (this.groupToDeleteId) {
      this.loading.set(true);
      this.service.deleteGroup(this.groupToDeleteId).subscribe({
        next: () => {
          this.isConfirmModalOpen.set(false);
          this.loadGroups();
        },
        error: (err: any) => {
          console.error('Error al eliminar grupo', err);
          this.loading.set(false);
          this.isConfirmModalOpen.set(false);
        }
      });
    }
  }

  onConfirmCancel(): void {
    this.isConfirmModalOpen.set(false);
    this.groupToDeleteId = null;
  }

  onAdd(): void {
    this.groupModalConfig.set(buildCreateGroupConfig());
    this.groupModalData.set(null);
    this.isGroupModalOpen.set(true);
  }

  onGroupSubmit(data: any): void {
    const isEdit = !!this.groupModalData();
    if (isEdit) {
      this.onUpdateSubmit(data);
    } else {
      this.onCreateSubmit(data);
    }
  }

  private onUpdateSubmit(data: any): void {
    this.loading.set(true);
    const updatedGroup = { ...this.groupModalData(), ...data };
    this.service.updateGroupById(updatedGroup).subscribe({
      next: () => {
        this.isGroupModalOpen.set(false);
        this.loadGroups();
      },
      error: (err) => {
        console.error('Error actualizando grupo', err);
        this.loading.set(false);
      }
    });
  }

  private onCreateSubmit(data: any): void {
    this.loading.set(true);
    this.service.createGroup(data).subscribe({
      next: () => {
        this.isGroupModalOpen.set(false);
        this.loadGroups(); // recargar para ver el nuevo
      },
      error: (err) => {
        console.error('Error creando grupo', err);
        this.loading.set(false);
      }
    });
  }

  onGroupCancel(): void {
    this.isGroupModalOpen.set(false);
  }

  // ── 7. Paginación ──────────────────────────────────────────────────────────
  onPageChange(page: number): void {
    this.loadGroups(page, this.pageSize);
  }

  onPageSizeChange(size: number): void {
    this.loadGroups(0, size);
  }
}
