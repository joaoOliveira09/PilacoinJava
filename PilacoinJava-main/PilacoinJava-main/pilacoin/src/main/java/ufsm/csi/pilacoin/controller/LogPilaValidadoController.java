package ufsm.csi.pilacoin.controller;

import org.springframework.web.bind.annotation.*;

import ufsm.csi.pilacoin.service.ValidaPila;

@RestController
@RequestMapping("/LogPilaValidado")
public class LogPilaValidadoController {

     private ValidaPila validaPila;

     public LogPilaValidadoController(ValidaPila validaPila) {
         this.validaPila = validaPila;
     }

     @GetMapping()
    public String getLogPilaValido(){

     return this.validaPila.Logs;

    }

}
