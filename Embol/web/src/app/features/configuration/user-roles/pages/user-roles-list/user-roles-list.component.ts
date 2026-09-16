import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DataTableTab } from '../../../../../shared/components/data-table/models/data-table.model';
import { UsuariosComponent } from '../../components/usuarios/usuarios.component';
import { RolesComponent } from '../../components/roles/roles.component';
import { GruposComponent } from '../../components/grupos/grupos.component';

@Component({
  selector: 'app-user-roles-list',
  standalone: true,
  imports: [CommonModule, UsuariosComponent, RolesComponent, GruposComponent],
  templateUrl: './user-roles-list.component.html',
})
export class UserRolesListComponent {

  // ── Estado de tabs ─────────────────────────────────────────────────────────
  activeTabId = signal<string>('usuarios');

  tabs = signal<DataTableTab[]>([
    { id: 'usuarios', label: 'Usuarios', active: true,  colorClass: 'bg-[#6B6661] text-white' },
    { id: 'roles',    label: 'Roles',    active: false, colorClass: 'bg-[#6B6661] text-white' },
    { id: 'grupos',   label: 'Grupos',   active: false, colorClass: 'bg-[#6B6661] text-white' },
  ]);

  // ── Helpers ────────────────────────────────────────────────────────────────
  onTabChange(tab: DataTableTab): void {
    this.tabs.update(ts => ts.map(t => ({ ...t, active: t.id === tab.id })));
    this.activeTabId.set(tab.id);
  }


}
