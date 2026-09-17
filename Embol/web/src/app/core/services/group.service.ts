import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Page } from '../../shared/models/pagination.model';
import { paginateArray } from '../../shared/utils/paginate.util';
import { GrupoRow } from '../../features/configuration/user-roles/models/grupos/grupo.model';
import { MOCK_GROUPS } from '../../features/configuration/user-roles/configs/mock-data.config';

@Injectable({ providedIn: 'root' })
export class GroupService {
  getPagedGroups(page = 0, size = 20): Observable<Page<GrupoRow>> {
    return paginateArray(MOCK_GROUPS, page, size);
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
