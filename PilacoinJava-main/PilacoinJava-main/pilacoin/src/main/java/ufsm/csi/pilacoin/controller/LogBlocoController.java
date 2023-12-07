package ufsm.csi.pilacoin.controller;

import org.springframework.web.bind.annotation.*;

import ufsm.csi.pilacoin.service.ValidaBlocoService;

@RestController
@RequestMapping("/LogBloco")
public class LogBlocoController {

    private ValidaBlocoService validaBlocoService;

     public LogBlocoController(ValidaBlocoService validaBlocoService) {
         this.validaBlocoService = validaBlocoService;
     }

     @GetMapping()
    public String getLogBloco(){

     return this.validaBlocoService.Logs;

    }

}
