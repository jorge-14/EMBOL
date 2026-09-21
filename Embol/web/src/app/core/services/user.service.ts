import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Page } from '../../shared/models/pagination.model';
import { UserRow }  from '../../features/configuration/user-roles/models/users/user.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class UserService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiBaseUrl}api/v1/tbl-usuario`;

  getPagedUsers(page = 0, size = 20, filters?: { role?: string | null, group?: string | null }): Observable<Page<UserRow>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', 'id')
      .set('sortDir', 'DESC');

    // Nota: El backend actual no parece recibir filtros de rol/grupo en paginated-user
    // pero si se agregaran, se pasarían aquí.

    return this.http.get<Page<UserRow>>(`${this.apiUrl}/paginated-user`, { params }).pipe(
      map(res => {
        if (res.content) {
          res.content = res.content.map(u => this.mapToUserRow(u));
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
  }

  getUserById(id: any): Observable<UserRow> {
    return this.http.get<any>(`${this.apiUrl}/get-user/${id}`).pipe(
      map(res => this.mapToUserRow(res.data))
    );
  }

  createUser(user: any): Observable<any> {
    const names = user.nombre.split(' ');
    const body = {
      username: user.email.split('@')[0],
      name: names[0],
      lastname: names.slice(1).join(' '),
      email: user.email,
      roleIds: user.roles || [],
      groupIds: user.grupos || []
    };
    // Endpoint anterior (solo BD local):
    // const oldBody = { ...body, userStatus: user.estado === 'Activo' ? 'ACTIVE' : 'INACTIVE' };
    // return this.http.post(`${this.apiUrl}/create-user`, oldBody);
    
    // Nuevo endpoint (SAGA Local + EntraID):
    return this.http.post(`${this.apiUrl}/saga`, body);
  }

  updateUser(id: any, user: any): Observable<any> {
    const names = user.nombre.split(' ');
    const body = {
      name: names[0],
      lastname: names.slice(1).join(' '),
      email: user.email,
      userStatus: user.estado === 'Activo' ? 'ACTIVE' : 'INACTIVE',
      roleIds: user.roles || [],
      groupIds: user.grupos || []
    };
    return this.http.put(`${this.apiUrl}/update-user/${id}`, body);
  }

  deleteUser(id: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/delete-user/${id}`, {});
  }

  private mapToUserRow(u: any): UserRow {
    const initials = ((u.name?.[0] || '') + (u.lastname?.[0] || '')).toUpperCase();
    return {
      id: u.id,
      initials: initials || '??',
      color: this.getRandomColor(u.name || ''),
      usuario: u.username,
      nombreCompleto: `${u.name || ''} ${u.lastname || ''}`.trim(),
      correo: u.email,
      planta: 'Planta Central', // Mockeado por ahora ya que no viene en DTO
      cargo: 'Colaborador',      // Mockeado por ahora ya que no viene en DTO
      roles: u.roleNames || [],
      grupos: u.groupNames || [],
      estado: u.userStatus === 'ACTIVE' ? 'Activo' : 'Inactivo'
    };
  }

  private getRandomColor(name: string): string {
    const colors = ['bg-blue-500', 'bg-green-500', 'bg-purple-500', 'bg-orange-500', 'bg-pink-500'];
    let hash = 0;
    for (let i = 0; i < name.length; i++) {
      hash = name.charCodeAt(i) + ((hash << 5) - hash);
    }
    return colors[Math.abs(hash) % colors.length];
  }
}
