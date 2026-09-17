import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../shared/models/pagination.model';
import { paginateArray } from '../../shared/utils/paginate.util';
import { RolRow }   from '../../features/configuration/user-roles/models/roles/rol.model';
import { MOCK_ROLES } from '../../features/configuration/user-roles/configs/mock-data.config';

@Injectable({ providedIn: 'root' })
export class RoleService {
  getPagedRoles(page = 0, size = 20): Observable<Page<RolRow>> {
    return paginateArray(MOCK_ROLES, page, size);
  }

  getRoleList(): Observable<RolRow[]> {
    return new Observable(obs => {
      obs.next(MOCK_ROLES);
      obs.complete();
    });
  }

  getRoleById(id: any): Observable<RolRow | undefined> {
    const role = MOCK_ROLES.find(r => r.id === id);
    return new Observable(obs => {
      obs.next(role);
      obs.complete();
    });
  }
}
