import { Observable, map } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Page } from '../../shared/models/pagination.model';
import { GrupoRow } from '../../features/configuration/user-roles/models/grupos/grupo.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
class GroupService {
  private http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiBaseUrl}api/v1/group`;

  getPagedGroups(page = 0, size = 20): Observable<Page<GrupoRow>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', 'id')
      .set('sortDir', 'DESC');

    return this.http.get<Page<GrupoRow>>(`${this.baseUrl}/paginated-group`, { params }).pipe(
      map(res => {
        if (res.content) {
          res.content = res.content.map(group => ({
            ...group,
            nombre: group.name,
            descripcion: group.description,
            estado: 'Activo' // El DTO del backend no parece traer estado, asignamos uno por defecto
          }));
        }
        res.page = {
          number: res.number,
          size: res.size,
          totalElements: res.totalElements,
          totalPages: res.totalPages
        };
        return res;
      })
    );

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
    // Si no hay un endpoint de lista corta, usamos el paginado con un tamaño grande
    const params = new HttpParams()
      .set('page', '0')
      .set('size', '1000')
      .set('sortBy', 'name')
      .set('sortDir', 'ASC');

    return this.http.get<Page<GrupoRow>>(`${this.baseUrl}/paginated-group`, { params }).pipe(
      map(res => (res.content || []).map(group => ({
        ...group,
        nombre: group.name,
        descripcion: group.description,
        estado: 'Activo'
      })))
    );
  }

  getGroupById(id: any): Observable<GrupoRow> {
    return this.http.get<GrupoRow>(`${this.baseUrl}/get-group/${id}`).pipe(
      map(group => ({
        ...group,
        nombre: group.name,
        descripcion: group.description,
        estado: 'Activo'
      }))
    );
  }

  createGroup(group: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/create-group`, group);
  }

  updateGroup(id: any, group: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/update-group/${id}`, group);
  }

  deleteGroup(id: any): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete-group/${id}`);
  }
}

export default GroupService
