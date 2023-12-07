package ufsm.csi.pilacoin.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;

import ufsm.csi.pilacoin.model.PilaCoin;
import ufsm.csi.pilacoin.repository.PilacoinRepository;

@RestController
@RequestMapping("/pilacoin")
public class PilaController {

    private PilacoinRepository pilacoinRepository;

    public PilaController(PilacoinRepository pilacoinRepository) {
        this.pilacoinRepository = pilacoinRepository;
    }

    @GetMapping()
    public ArrayList<PilaCoin> getPilas() {
        return (ArrayList<PilaCoin>) this.pilacoinRepository.findAll();
    }

    @GetMapping("/{id}")
    public PilaCoin getPila(@PathVariable("id") Long id) {
        return this.pilacoinRepository.findById(id).get();
    }
    
}
