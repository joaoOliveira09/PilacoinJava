package ufsm.csi.pilacoin.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ufsm.csi.pilacoin.service.MineraPilaService;

@RestController
@RequestMapping("/minerar")
public class MineraPilaController {
  
    private final MineraPilaService mineraPilaService;

    public MineraPilaController(MineraPilaService mineraPilaService) {
        this.mineraPilaService = mineraPilaService;
    }

    @PostMapping("/iniciar")
    public void iniciarMineracao(@RequestParam int threads) {
        mineraPilaService.minerar(threads);
    }
    
}
