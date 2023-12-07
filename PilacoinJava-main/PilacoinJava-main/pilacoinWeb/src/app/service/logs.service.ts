import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable, catchError, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogsService {

  private baseApiUrl = environment.API_URL
  private apiUrlPila = `${this.baseApiUrl}LogPila`
  private apiUrlPilaValidado = `${this.baseApiUrl}LogPilaValidado`
  private apiUrlBloco = `${this.baseApiUrl}LogBloco`
  private apiUrlBlocoValidado = `${this.baseApiUrl}LogBlocoValidado`

  constructor(private http: HttpClient) {
  }

  getLogPila(): Observable<string> {
    return this.http.get(this.apiUrlPila,{ responseType: 'text' })
  }

  getLogPilaValidado(): Observable<string> {
    return this.http.get(this.apiUrlPilaValidado,{ responseType: 'text' })
  }

  getLogBloco(): Observable<string> {
    return this.http.get(this.apiUrlBloco,{ responseType: 'text' })
  }

  getLogBlocoValidado(): Observable<string> {
    return this.http.get(this.apiUrlBlocoValidado,{ responseType: 'text' })
  }

}
