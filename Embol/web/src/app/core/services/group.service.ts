import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Page } from '../../shared/models/pagination.model';
import { paginateArray } from '../../shared/utils/paginate.util';
import { GrupoRow } from '../../features/configuration/user-roles/models/grupos/grupo.model';
import { MOCK_GROUPS } from '../../features/configuration/user-roles/configs/mock-data.config';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class GroupService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiBaseUrl}api/v1/group`;

  getPagedGroups(page = 0, size = 20): Observable<Page<GrupoRow>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', 'ID') // o el campo por defecto
      .set('sortDir', 'DESC');

    return this.http.get<Page<any>>(`${this.baseUrl}/paginated-group`, { params })
      .pipe(
        map((response: any) => {
          return {
            content: response.content,
            page: {
              size: response.size,
              number: response.number,
              totalElements: response.totalElements,
              totalPages: response.totalPages
            }
          } as Page<GrupoRow>;
        })
      );
  }

  getGroupList(): Observable<GrupoRow[]> {
    return new Observable(obs => {
      obs.next(MOCK_GROUPS);
      obs.complete();
    });
  }

  getGroupById(id: any): Observable<GrupoRow | undefined> {
    const group = MOCK_GROUPS.find(g => g.id === id);
    return new Observable(obs => {
      obs.next(group);
      obs.complete();
    });
  }
}
