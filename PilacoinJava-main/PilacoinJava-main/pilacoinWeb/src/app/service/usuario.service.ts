import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, catchError, of, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Usuario } from '../interfaces/Usuario';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private baseApiUrl = environment.API_URL
  private apiUrl = `${this.baseApiUrl}usuario`
  constructor(private http: HttpClient,
    private router: Router) { }

  getUsers(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl)
  }

  getUser(id: number): Observable<Usuario> {
    const url = `${this.apiUrl}/${id}`
    return this.http.get<Usuario>(url)
  }
}
