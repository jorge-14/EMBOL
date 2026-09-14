import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { Page } from '../../../../shared/models/pagination.model';
import { environment } from '../../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private http = inject(HttpClient);

  get apiUrl(): string {
    return `${environment.apiBaseUrl}users/paged`;
  }

  getUsers(page: number = 0, size: number = 20, filter: string = ''): Observable<Page<User>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (filter) {
      params = params.set('filter', filter);
    }

    return this.http.get<Page<User>>(this.apiUrl, { params });
  }

  getHello(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/hello`);
  }
}
