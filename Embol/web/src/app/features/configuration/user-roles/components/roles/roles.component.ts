import { Component, OnInit, inject, signal } from '@angular/core';
import { ROLES_IMPORTS } from '../../enums/user-roles-imports';
import { RoleService } from '../../../../../core/services/role.service';
import { RolRow } from '../../models/roles/rol.model';
import { DataTableColumn, DataTableRowAction } from '../../../../../shared/components/data-table/models/data-table.model';
import { buildRolesColumns } from '../../configs/roles/roles-columns.config';
import { buildRolRowActions } from '../../configs/roles/roles-actions.config';
import { buildRolConfig } from '../../configs/roles/roles-form.config';
import { DynamicFormConfig } from '../../../../../shared/components/dynamic-form/models/dynamic-form.model';
import { PageMetadata } from '../../../../../shared/models/pagination.model';
import { ConfirmModalConfig } from '../../../../../shared/components/confirm-modal/models/confirm-modal.model';

@Component({
  selector: 'app-roles',
  standalone: true,
  imports: [...ROLES_IMPORTS],
  templateUrl: './roles.component.html',
})
export class RolesComponent implements OnInit {

  // ── 1. Dependencias ────────────────────────────────────────────────────────
  private service = inject(RoleService);

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

  isRoleModalOpen = signal(false);
  roleModalConfig = signal<DynamicFormConfig | null>(null);
  roleModalData = signal<any>(null);

  // Modal de Confirmación para eliminar
  isConfirmModalOpen = signal(false);
  confirmModalConfig = signal<ConfirmModalConfig>({
    title: '',
    description: ''
  });
  private roleToDeleteId: any = null;

  // ── 6. Acciones de fila ────────────────────────────────────────────────────
  onEdit(id: any): void {
    this.loading.set(true);
    this.service.getRoleById(id).subscribe({
      next: (role) => {
        this.loading.set(false);
        if (!role) return;

        this.roleModalConfig.set(buildRolConfig(true));
        this.roleModalData.set({
          id: role.id,
          nombre: role.nombre,
          descripcion: role.descripcion,
          estado: role.estado
        });
        this.isRoleModalOpen.set(true);
      },
      error: (err) => {
        console.error('Error obteniendo rol por ID', err);
        this.loading.set(false);
      }
    });
  }

  onDelete(id: any): void {
    this.roleToDeleteId = id;
    this.confirmModalConfig.set({
      title: 'Eliminar Rol',
      description: '¿Está seguro de que desea eliminar este rol? Esta acción no se puede deshacer.',
      confirmLabel: 'Eliminar',
      cancelLabel: 'Cancelar',
      icon: 'danger'
    });
    this.isConfirmModalOpen.set(true);
  }

  onConfirmDelete(): void {
    if (this.roleToDeleteId) {
      this.loading.set(true);
      this.service.deleteRole(this.roleToDeleteId).subscribe({
        next: () => {
          this.isConfirmModalOpen.set(false);
          this.loadRoles();
        },
        error: (err) => {
          console.error('Error al eliminar rol', err);
          this.loading.set(false);
          this.isConfirmModalOpen.set(false);
        }
      });
    }
  }

  onConfirmCancel(): void {
    this.isConfirmModalOpen.set(false);
    this.roleToDeleteId = null;
  }

  onAdd(): void {
    this.roleModalConfig.set(buildRolConfig(false));
    this.roleModalData.set(null);
    this.isRoleModalOpen.set(true);
  }

  onRoleSubmit(data: any): void {
    const isEdit = !!this.roleModalData();
    if (isEdit) {
      this.onUpdateSubmit(data);
    } else {
      this.onCreateSubmit(data);
    }
  }

  private onUpdateSubmit(data: any): void {
    this.loading.set(true);
    this.service.updateRole(this.roleModalData().id, data).subscribe({
      next: () => {
        this.isRoleModalOpen.set(false);
        this.loadRoles();
      },
      error: (err) => {
        console.error('Error actualizando rol', err);
        this.loading.set(false);
      }
    });
  }

  private onCreateSubmit(data: any): void {
    this.loading.set(true);
    this.service.createRole(data).subscribe({
      next: () => {
        this.isRoleModalOpen.set(false);
        this.loadRoles();
      },
      error: (err) => {
        console.error('Error creando rol', err);
        this.loading.set(false);
      }
    });
  }

  onRoleCancel(): void {
    this.isRoleModalOpen.set(false);
  }

  // ── 7. Paginación ──────────────────────────────────────────────────────────
  onPageChange(page: number): void {
    this.loadRoles(page, this.pageSize);
  }

  onPageSizeChange(size: number): void {
    this.loadRoles(0, size);
  }
}
