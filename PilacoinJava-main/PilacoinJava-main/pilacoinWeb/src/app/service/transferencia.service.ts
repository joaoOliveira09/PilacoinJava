import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, catchError, of, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Transferencia } from '../interfaces/Transferencia';
@Injectable({
  providedIn: 'root'
})
export class TransferenciaService {
  private baseApiUrl = environment.API_URL
  private apiUrl = `${this.baseApiUrl}transferencia`

  constructor(private http: HttpClient,
     private router: Router) { }

    // metodo para gerar a transferencia tentar fazer ele só passando id do pila e id do usuário
    transferir(usuarioId: number, pilaId: number): Observable<any> {
      console.log("usuario"+usuarioId)
      console.log("pila"+pilaId)
      const requestData = {
        usuarioId: usuarioId,
        pilaId: pilaId,
      };

      return this.http.post<any>(this.apiUrl, requestData).pipe(
        tap((response) => {

          console.log(response);
          this.router.navigate(['/home']);
        }),
        catchError((error) => {

          return of(false);
        })
      );
    }
}
