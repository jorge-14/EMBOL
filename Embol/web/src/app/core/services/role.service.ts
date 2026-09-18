import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Page } from '../../shared/models/pagination.model';
import { RolRow }   from '../../features/configuration/user-roles/models/roles/rol.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class RoleService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiBaseUrl}api/v1/tbl-role`;

  getPagedRoles(page = 0, size = 20): Observable<Page<RolRow>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', 'id')
      .set('sortDir', 'DESC');

    return this.http.get<Page<RolRow>>(`${this.apiUrl}/page-rol`, { params }).pipe(
      map(res => {
        // Mapeamos los campos del backend a los que espera el componente si es necesario
        if (res.content) {
          res.content = res.content.map(rol => ({
            ...rol,
            nombre: rol.name || rol.nombre,
            descripcion: rol.description || rol.descripcion,
            estado: (rol.roleStatus || rol.estado) === 'ACTIVE' ? 'Activo' : (rol.roleStatus || rol.estado) === 'INACTIVE' ? 'Inactivo' : (rol.roleStatus || rol.estado)
          }));
        }
        // Aseguramos que pageInfo esté poblado para el componente
        res.page = {
          number: res.number,
          size: res.size,
          totalElements: res.totalElements,
          totalPages: res.totalPages
        };
        return res;
      })
    );
  }

  getRoleList(): Observable<RolRow[]> {
    return this.http.get<any[]>(`${this.apiUrl}/list-role-short`).pipe(
      map(roles => roles.map(rol => ({
        id: rol.id,
        nombre: rol.name,
        descripcion: '',
        estado: 'Activo'
      })))
    );
  }

  getRoleById(id: any): Observable<RolRow> {
    return this.http.get<any>(`${this.apiUrl}/get-role/${id}`).pipe(
      map(res => {
        const rol = res.data;
        return {
          ...rol,
          nombre: rol.name || rol.nombre,
          descripcion: rol.description || rol.descripcion,
          estado: (rol.roleStatus || rol.estado) === 'ACTIVE' ? 'Activo' : (rol.roleStatus || rol.estado) === 'INACTIVE' ? 'Inactivo' : (rol.roleStatus || rol.estado)
        };
      })
    );
  }

  createRole(role: any): Observable<any> {
    const body = {
      name: role.nombre,
      description: role.descripcion,
      roleStatus: role.estado === 'Activo' ? 'ACTIVE' : 'INACTIVE',
      baseRole: false
    };
    return this.http.post(`${this.apiUrl}/create-role`, body);
  }

  updateRole(id: any, role: any): Observable<any> {
    const body = {
      name: role.nombre,
      description: role.descripcion,
      roleStatus: role.estado === 'Activo' ? 'ACTIVE' : 'INACTIVE'
    };
    return this.http.put(`${this.apiUrl}/update-role/${id}`, body);
  }

  deleteRole(id: any): Observable<any> {
    return this.http.delete(`${this.apiUrl}/delete-role/${id}`);
  }
}
