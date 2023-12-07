import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, catchError, of, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Pilacoin } from '../interfaces/Pilacoin';
@Injectable({
  providedIn: 'root'
})
export class PilacoinService {
  private baseApiUrl = environment.API_URL
  private apiUrl = `${this.baseApiUrl}pilacoin`

  constructor(private http: HttpClient,
    private router: Router) { }

    getPilas(): Observable<Pilacoin[] | boolean> {
      console.log("passei aqui")
      return this.http.get<Pilacoin[]>(this.apiUrl).pipe(
        catchError((e) => {
          console.warn(e)

          return of (false)
        })
      )
    }
  
    getPila(id: number): Observable<Pilacoin> {
      const url = `${this.apiUrl}/${id}`
  
      return this.http.get<Pilacoin>(url)
    }  
}
