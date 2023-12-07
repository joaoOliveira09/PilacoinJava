package ufsm.csi.pilacoin.controller;

import org.springframework.web.bind.annotation.*;

import ufsm.csi.pilacoin.service.DificuldadeService;
import ufsm.csi.pilacoin.service.MineraPilaService;

@RestController
@RequestMapping("/LogPila")
public class LogPilaController {

     private DificuldadeService dificuldadeService;

    public LogPilaController(DificuldadeService dificuldadeService) {
        this.dificuldadeService = dificuldadeService;
     }

 @GetMapping()
    public String getLogPila(){
     System.out.println("PRGUEI OS PILAS");
     return this.dificuldadeService.LogsPila;

    }
}
