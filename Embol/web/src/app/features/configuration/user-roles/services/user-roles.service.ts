// ─── UserRoles Service ────────────────────────────────────────────────────────
// Datos de prueba. Para conectar el backend real:
//   1. Inyecta HttpClient
//   2. Reemplaza paginateArray(...) por this.http.get<Page<T>>(url, { params })
//   3. Para timeout de peticiones, agrégalo con el operador RxJS timeout()
//      o configúralo en un HttpInterceptor global (recomendado).

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../../../shared/models/pagination.model';
import { paginateArray } from '../../../../shared/utils/paginate.util';
import { UserRow }  from '../models/users/user.model';
import { RolRow }   from '../models/roles/rol.model';
import { GrupoRow } from '../models/grupos/grupo.model';
import { MOCK_USERS, MOCK_ROLES, MOCK_GROUPS } from '../configs/mock-data.config';

@Injectable({ providedIn: 'root' })
export class UserRolesService {

  // ── Usuarios ────────────────────────────────────────────────────────────────
  getPagedUsers(page = 0, size = 20): Observable<Page<UserRow>> {
    return paginateArray(MOCK_USERS, page, size);
  }

  // ── Roles ───────────────────────────────────────────────────────────────────
  getPagedRoles(page = 0, size = 20): Observable<Page<RolRow>> {
    return paginateArray(MOCK_ROLES, page, size);
  }

  // ── Grupos ──────────────────────────────────────────────────────────────────
  getPagedGroups(page = 0, size = 20): Observable<Page<GrupoRow>> {
    return paginateArray(MOCK_GROUPS, page, size);
  }
}
