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
      .set('sortBy', 'id')
      .set('sortDir', 'DESC');

    return this.http.get<Page<any>>(`${this.baseUrl}/paginated-group`, { params })
      .pipe(
        map((response: any) => {
          return {
            content: (response.content || []).map((g: any) => ({
              id: g.id,
              nombre: g.name || g.nombre,
              descripcion: g.description || g.descripcion,
              status: g.status
            })),
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
    return this.http.get<Page<any>>(`${this.baseUrl}/paginated-group`, {
      params: new HttpParams().set('page', '0').set('size', '1000')
    }).pipe(
      map(response => (response.content || []).map((g: any) => ({
        id: g.id,
        nombre: g.name || g.nombre,
        descripcion: g.description || g.descripcion,
        status: g.status
      })))
    );
  }

  getGroupById(id: any): Observable<GrupoRow | undefined> {
    return this.http.get<any>(`${this.baseUrl}/information-group-by-id/${id}`).pipe(
      map(res => {
        const g = res.data;
        if (!g) return undefined;
        return {
          id: g.id,
          nombre: g.name || g.nombre,
          descripcion: g.description || g.descripcion,
          status: g.status
        } as GrupoRow;
      })
    );
  }

  updateGroupById(data: GrupoRow): Observable<GrupoRow> {
    const body = {
      id: data.id,
      name: data.nombre,
      description: data.descripcion,
      status: data.status || 'Activo'
    };
    return this.http.put<GrupoRow>(`${this.baseUrl}/update-group/${data.id}`, body);
  }

  createGroup(data: GrupoRow): Observable<GrupoRow> {
    const body = {
      name: data.nombre,
      description: data.descripcion
    };
    // Endpoint anterior (solo BD local):
    // const oldBody = { ...body, status: data.status || 'Activo' };
    // return this.http.post<GrupoRow>(`${this.baseUrl}/create-group`, oldBody);

    // Nuevo endpoint (SAGA Local + EntraID):
    return this.http.post<GrupoRow>(`${this.baseUrl}/saga`, body);
  }

  deleteGroup(id: any): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete-group/${id}`);
  }
}
