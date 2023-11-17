//package ufsm.csi.pilacoin.service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ufsm.csi.pilacoin.model.PilaCoin;
//import ufsm.csi.pilacoin.model.ValidacaoPila;
//import ufsm.csi.pilacoin.repository.ValidacaoPilaRepository;
//
//@Service
//public class ValidacaoPilaCoinServiceBanco {
//    private final ValidacaoPilaRepository validacaoPilaRepository;
//
//    @Autowired
//    public ValidacaoPilaCoinServiceBanco(ValidacaoPilaRepository validacaoPilaRepository) {
//        this.validacaoPilaRepository = validacaoPilaRepository;
//    }
//
//    public PilaCoin saveValidacaoPila(PilaCoin pilaCoin) {
//        System.out.println("to guardando no banco as validações");
//        return validacaoPilaRepository.save(pilaCoin);
//    }
//
//    public PilaCoin getValidacaoPilaById(Long id) {
//        return validacaoPilaRepository.findById(id).orElse(null);
//    }
//
//
//}
