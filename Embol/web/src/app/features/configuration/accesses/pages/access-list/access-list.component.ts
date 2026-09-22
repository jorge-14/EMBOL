import { Component, computed, signal, viewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccessTableComponent, PermissionChange, PropagatePermission } from '../../../../../shared/components/access-table/access-table.component';
import { AccessTableConfig } from '../../../../../shared/components/access-table/models/access-table.model';
import { AccessSummaryModalComponent } from '../../../../../shared/components/access-summary-modal/access-summary-modal.component';
import { MOCK_GROUPS, MOCK_ROLES } from '../../../user-roles/configs/mock-data.config';

type Mode = 'rol' | 'grupo';

const newPermissionDefaults = {
  descargar_json: false, importar_json: false,
  descargar_pdf: false, importar_pdf: false,
  descargar_excel: false, importar_excel: false,
  descargar_svg: false, importar_svg: false,
  rechazar: false, publicar: false, archivar: false,
  duplicar: false, compartir: false,
};

const initialConfig: AccessTableConfig = {
  actions: [
    { key: 'ver', label: 'Ver' }, { key: 'crear', label: 'Crear' },
    { key: 'modificar', label: 'Modificar' }, { key: 'eliminar', label: 'Eliminar' },
    { key: 'descargar_png', label: 'Descargar PNG' }, { key: 'exportar_png', label: 'Exportar PNG' },
    { key: 'descargar_json', label: 'Descargar JSON' }, { key: 'importar_json', label: 'Importar JSON' },
    { key: 'descargar_pdf', label: 'Descargar PDF' }, { key: 'importar_pdf', label: 'Importar PDF' },
    { key: 'descargar_excel', label: 'Descargar Excel' }, { key: 'importar_excel', label: 'Importar Excel' },
    { key: 'descargar_svg', label: 'Descargar SVG' }, { key: 'importar_svg', label: 'Importar SVG' },
    { key: 'aprobar', label: 'Aprobar' },
    { key: 'rechazar', label: 'Rechazar' }, { key: 'publicar', label: 'Publicar' },
    { key: 'archivar', label: 'Archivar' }, { key: 'duplicar', label: 'Duplicar' },
    { key: 'compartir', label: 'Compartir' },
  ],
  groups: [
    { name: 'Administración', resources: [
      { name: 'Usuarios', permissions: { ver: true, crear: true, modificar: false, eliminar: true, descargar_png: null, exportar_png: null, aprobar: null, ...newPermissionDefaults } },
      { name: 'Roles y Grupos', permissions: { ver: true, crear: true, modificar: true, eliminar: true, descargar_png: null, exportar_png: null, aprobar: null, ...newPermissionDefaults } },
      { name: 'Parámetros del Sistema', permissions: { ver: true, crear: null, modificar: true, eliminar: null, descargar_png: null, exportar_png: null, aprobar: true, ...newPermissionDefaults } },
    ] },
    { name: 'Simulador', resources: [
      { name: 'Simulador Salarial', permissions: { ver: true, crear: true, modificar: true, eliminar: false, descargar_png: null, exportar_png: true, aprobar: true, ...newPermissionDefaults } },
      { name: 'Escenarios', permissions: { ver: true, crear: true, modificar: true, eliminar: true, descargar_png: null, exportar_png: null, aprobar: true, ...newPermissionDefaults } },
      { name: 'Reportes Simulador', permissions: { ver: true, crear: null, modificar: null, eliminar: null, descargar_png: true, exportar_png: true, aprobar: null, ...newPermissionDefaults } },
    ] },
  ],
};

function cloneConfig(config: AccessTableConfig): AccessTableConfig {
  return {
    actions: config.actions,
    groups: config.groups.map(group => ({
      name: group.name,
      resources: group.resources.map(resource => ({ name: resource.name, permissions: { ...resource.permissions } })),
    })),
  };
}

@Component({
  selector: 'app-access-list',
  standalone: true,
  imports: [CommonModule, FormsModule, AccessTableComponent, AccessSummaryModalComponent],
  templateUrl: './access-list.component.html',
  styleUrl: './access-list.component.css',
})
export class AccessListComponent {
  private readonly accessTable = viewChild(AccessTableComponent);
  readonly roles = MOCK_ROLES.map(role => role.nombre);
  readonly groups = MOCK_GROUPS.map(group => group.nombre);
  readonly mode = signal<Mode>('rol');
  readonly selectedByMode = signal<Record<Mode, string>>({ rol: 'Finanzas', grupo: 'Finanzas' });
  readonly selectedPrincipal = computed(() => this.selectedByMode()[this.mode()]);
  readonly principalOptions = computed(() => this.mode() === 'rol' ? this.roles : this.groups);
  readonly isSummaryOpen = signal(false);

  private readonly saved = signal<Record<string, AccessTableConfig>>({});
  private readonly drafts = signal<Record<string, AccessTableConfig>>({});
  readonly principalKey = computed(() => `${this.mode()}:${this.selectedPrincipal()}`);
  readonly tableConfig = computed(() => this.drafts()[this.principalKey()] ?? this.saved()[this.principalKey()] ?? initialConfig);
  readonly pendingKeys = computed(() => {
    const original = this.saved()[this.principalKey()] ?? initialConfig;
    const current = this.tableConfig();
    const keys = new Set<string>();
    current.groups.forEach((group, groupIndex) => group.resources.forEach((resource, resourceIndex) => {
      for (const action of current.actions) {
        if (resource.permissions[action.key] !== original.groups[groupIndex].resources[resourceIndex].permissions[action.key]) {
          keys.add(`${group.name}::${resource.name}::${action.key}`);
        }
      }
    }));
    return keys;
  });
  readonly pendingCount = computed(() => {
    let count = 0;
    for (const key of Object.keys(this.drafts())) {
      const original = this.saved()[key] ?? initialConfig;
      const draft = this.drafts()[key];
      draft.groups.forEach((group, groupIndex) => group.resources.forEach((resource, resourceIndex) => {
        for (const action of draft.actions) {
          if (resource.permissions[action.key] !== original.groups[groupIndex].resources[resourceIndex].permissions[action.key]) count++;
        }
      }));
    }
    return count;
  });
  readonly permissionTotals = computed(() => {
    let granted = 0;
    let applicable = 0;
    for (const group of this.tableConfig().groups) {
      for (const resource of group.resources) {
        for (const action of this.tableConfig().actions) {
          const value = resource.permissions[action.key];
          if (value !== null) applicable++;
          if (value === true) granted++;
        }
      }
    }
    return { granted, applicable, percentage: applicable ? Math.round(granted / applicable * 100) : 0 };
  });

  setMode(mode: Mode): void {
    if (mode === this.mode()) return;
    this.accessTable()?.clearSelected();
    this.accessTable()?.closePopover();
    this.mode.set(mode);
  }
  selectPrincipal(name: string): void {
    this.accessTable()?.clearSelected();
    this.accessTable()?.closePopover();
    this.selectedByMode.update(selected => ({ ...selected, [this.mode()]: name }));
  }
  toggleSummary(): void { this.isSummaryOpen.update(open => !open); }

  onPermissionChange(change: PermissionChange): void { this.updatePermission(this.principalKey(), change); }
  onPropagate(event: PropagatePermission): void {
    for (const target of event.targets) this.updatePermission(`${this.mode()}:${target}`, event);
  }

  private updatePermission(principal: string, change: PermissionChange): void {
    const current = this.drafts()[principal] ?? this.saved()[principal] ?? initialConfig;
    const updated = cloneConfig(current);
    const resource = updated.groups.find(group => group.name === change.group)?.resources.find(item => item.name === change.resource);
    if (!resource || resource.permissions[change.action] === null || resource.permissions[change.action] === change.value) return;
    resource.permissions[change.action] = change.value;
    this.drafts.update(drafts => ({ ...drafts, [principal]: updated }));
  }

  discardChanges(): void { this.drafts.set({}); }
  saveChanges(): void {
    if (!this.pendingCount()) return;
    this.saved.update(saved => ({ ...saved, ...this.drafts() }));
    this.drafts.set({});
  }
}
