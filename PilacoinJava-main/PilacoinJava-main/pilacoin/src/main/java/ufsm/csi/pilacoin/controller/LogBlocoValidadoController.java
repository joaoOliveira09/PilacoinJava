package ufsm.csi.pilacoin.controller;

import org.springframework.web.bind.annotation.*;

import ufsm.csi.pilacoin.service.ValidaBlocoService;

@RestController
@RequestMapping("/LogBlocoValidado")
public class LogBlocoValidadoController {

    private ValidaBlocoService validaBlocoService;


     public LogBlocoValidadoController(ValidaBlocoService validaBlocoService) {
         this.validaBlocoService = validaBlocoService;
     }

     @GetMapping()
    public String getLogBlocoValidado(){

     return this.validaBlocoService.LogsValidado;

    }

}
