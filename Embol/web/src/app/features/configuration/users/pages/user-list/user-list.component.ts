import { Component, OnInit, inject, signal, OnDestroy } from '@angular/core';
import { USER_IMPORTS } from '../../enums/user-imports';
import { User } from '../../models/user.model';
import { Page } from '../../../../../shared/models/pagination.model';
import { UserService } from '../../services/user.service';
// import Keycloak from 'keycloak-js';
import { Subject, Subscription } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { DataTableColumn } from '../../../../../shared/components/data-table/models/data-table.model';
import { HorizontalSelectComponent } from '../../../../../shared/components/horizontal-controls/horizontal-select/horizontal-select.component';
import { SelectOption } from '../../../../../shared/components/horizontal-controls/models/select-option.model';
import { HorizontalMultiSelectComponent } from '../../../../../shared/components/horizontal-controls/horizontal-multi-select/horizontal-multi-select.component';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [
    ...USER_IMPORTS,
    HorizontalSelectComponent,
    HorizontalMultiSelectComponent
  ],
  templateUrl: './user-list.component.html',
})
export class UserListComponent implements OnInit {
  private userService = inject(UserService);
  // private keycloak = inject(Keycloak);

  usersData = signal<Page<User> | null>(null);
  loading = signal<boolean>(true);
  error = signal<string | null>(null);
  filterQuery = signal<string>('');
  pageSize = signal<number>(5);

  roleFilter = signal<string | null>(null);
  statusFilter = signal<any[]>([]);

  roleOptions: SelectOption[] = [
    { label: 'Administrador', value: 'ADMIN' },
    { label: 'Operador', value: 'OPERATOR' }
  ];

  statusOptions: SelectOption[] = [
    { label: 'Activo', value: 'ENABLED' },
    { label: 'Inactivo', value: 'DISABLED' }
  ];

  columns = signal<DataTableColumn[]>([
    {
      key: 'usuario',
      header: 'Usuario',
      type: 'text',
      formatter: (_, row) => `${row.firstName} ${row.lastName}`
    },
    {
      key: 'email',
      header: 'Email',
      type: 'text',
      cssClass: 'text-brand'
    },
    { key: 'username', header: 'Username', type: 'text' },
    {
      key: 'status',
      header: 'Estado',
      type: 'badge',
      badgeColorMap: {
        'ENABLED': 'bg-green-100 text-green-800 border-green-200',
        'DISABLED': 'bg-red-100 text-red-800 border-red-200'
      }
    },
    {
      key: 'profile',
      header: 'Perfil',
      type: 'text',
      formatter: (_, row) => row.profile?.name || 'N/A'
    },
    { key: 'phone', header: 'Teléfono', type: 'text' }
  ]);

  ngOnInit(): void {
    this.loadUsers(0);
  }

  loadUsers(page: number): void {
    this.loading.set(true);
    this.error.set(null);

    this.userService.getUsers(page, this.pageSize(), this.filterQuery()).subscribe({
      next: (data) => {
        this.usersData.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error fetching users', err);
        this.error.set(err.message || '-Failed to communicate with the backend server.');
        this.loading.set(false);
      }
    });
  }

  changePage(page: number): void {
    const data = this.usersData();
    if (data && page >= 0 && page < data.page.totalPages) {
      this.loadUsers(page);
    }
  }

  changePageSize(size: number): void {
    this.pageSize.set(size);
    this.loadUsers(0);
  }

  logout(): void {
    // this.keycloak.logout();
    console.log('Logout placeholder');
  }

  protected onSearch(term: string): void {
    console.log('Buscar en servidor:', term);
    this.filterQuery.set(term);

    this.loadUsers(0);
  }

  clearFilters(): void {
    this.roleFilter.set(null);
    this.statusFilter.set([]);
    this.filterQuery.set('');
    this.loadUsers(0);
  }
}
