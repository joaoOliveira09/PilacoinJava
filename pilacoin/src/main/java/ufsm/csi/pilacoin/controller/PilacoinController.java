package ufsm.csi.pilacoin.controller;

import org.springframework.web.bind.annotation.*;

import ufsm.csi.pilacoin.model.PilaCoin;
import ufsm.csi.pilacoin.service.PilacoinService;

@RestController
@RequestMapping("/minerar")
public class PilacoinController {
  
    private final PilacoinService pilacoinService;

    public PilacoinController(PilacoinService pilacoinService) {
        this.pilacoinService = pilacoinService;
    }

    @PostMapping("/save")
    public PilaCoin savePila(@RequestBody PilaCoin pilaCoin) {
        return pilacoinService.savePila(pilaCoin);
    }
    
}
