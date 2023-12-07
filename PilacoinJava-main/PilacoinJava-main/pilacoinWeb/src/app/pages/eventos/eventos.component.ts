import { Component, OnInit } from '@angular/core';
import { LogsService } from 'src/app/service/logs.service';

@Component({
  selector: 'app-eventos',
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.css']
})
export class EventosComponent implements OnInit {
  logsPila: string = '';
  logsPilaValidado: string = '';
  logsBloco: string = '';
  logsBlocoValidado: string = '';

  constructor(private logsService: LogsService) { }

  ngOnInit(): void {
    this.logsService.getLogPila().subscribe(
      (data) => {
        this.logsPila = data;
      }
    );

    this.logsService.getLogPilaValidado().subscribe(
      (data) => {
        this.logsPilaValidado = data;
      }
    );

    this.logsService.getLogBloco().subscribe(
      (data) => {
        this.logsBloco = data;
      }
    );

    this.logsService.getLogBlocoValidado().subscribe(
      (data) => {
        this.logsBlocoValidado = data;
      }
    );


  }

}
