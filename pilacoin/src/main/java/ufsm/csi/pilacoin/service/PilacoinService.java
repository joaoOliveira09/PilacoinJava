package ufsm.csi.pilacoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufsm.csi.pilacoin.model.PilaCoin;
import ufsm.csi.pilacoin.repository.PilacoinRepository;

@Service
public class PilacoinService {
    public final PilacoinRepository pilacoinRepository;

    @Autowired
    public PilacoinService(PilacoinRepository pilacoinRepository){
        this.pilacoinRepository = pilacoinRepository;
    }

    public PilaCoin savePila(PilaCoin pilaCoin){
        System.out.println("Passei aqui GREMIOOOOOOOOO");
        return pilacoinRepository.save(pilaCoin);
    }
}
