import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../shared/models/pagination.model';
import { paginateArray } from '../../shared/utils/paginate.util';
import { UserRow }  from '../../features/configuration/user-roles/models/users/user.model';
import { MOCK_USERS } from '../../features/configuration/user-roles/configs/mock-data.config';

@Injectable({ providedIn: 'root' })
export class UserService {
  getPagedUsers(page = 0, size = 20, filters?: { role?: string | null, group?: string | null }): Observable<Page<UserRow>> {
    let data = [...MOCK_USERS];
    
    if (filters) {
      if (filters.role) {
        data = data.filter(u => u.roles && u.roles.includes(filters.role!));
      }
      if (filters.group) {
        data = data.filter(u => u.grupos && u.grupos.includes(filters.group!));
      }
    }
    
    return paginateArray(data, page, size);
  }

  getUserById(id: any): Observable<UserRow | undefined> {
    const user = MOCK_USERS.find(u => u.id === id);
    return new Observable(obs => {
      obs.next(user);
      obs.complete();
    });
  }
}
