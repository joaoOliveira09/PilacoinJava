package ufsm.csi.pilacoin.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Optional;

import javax.crypto.Cipher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.SneakyThrows;
import ufsm.csi.pilacoin.model.PilaCoin;
import ufsm.csi.pilacoin.model.TransferenciaPila;
import ufsm.csi.pilacoin.model.Usuario;
import ufsm.csi.pilacoin.repository.PilacoinRepository;
import ufsm.csi.pilacoin.repository.UsuarioRepository;
import ufsm.csi.pilacoin.service.RequisisaoService;
import ufsm.csi.pilacoin.utils.Chaves;


@RestController
@RequestMapping("/transferencia")
public class TranferenciaController {

    private final PilacoinRepository pilacoinRepository;
    private final UsuarioRepository usuarioRepository;

    private final RequisisaoService requisisaoService;

    public static PrivateKey privateKey;
    public PublicKey publicKey;

    @Data
    private static class TranferRequest{
        private Long usuarioId;
        private Long pilaId; 
    }

    public TranferenciaController(PilacoinRepository pilacoinRepository, UsuarioRepository usuarioRepository, RequisisaoService requisisaoService) {
        this.pilacoinRepository = pilacoinRepository;
        this.usuarioRepository = usuarioRepository;
        this.requisisaoService = requisisaoService;
    }
    

    //@Scheduled(initialDelay = 5000,fixedDelay = 5000)
    //@SneakyThrows
    @PostMapping()
    public ResponseEntity<Object> tranferirPila(@RequestBody TranferRequest tranferRequest) {
        System.out.println(tranferRequest.usuarioId);
        if (tranferirPilaReal(tranferRequest)) {
            return new ResponseEntity<Object>(tranferRequest, HttpStatus.OK);
        }

        return new ResponseEntity<Object>("erro de transferência", HttpStatus.BAD_REQUEST);
    }
    
    public Boolean tranferirPilaReal(TranferRequest tranferRequest) {
        try {
        Optional<PilaCoin> optionalPilaCoinJson = pilacoinRepository.findById(tranferRequest.pilaId);
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(tranferRequest.usuarioId);

        // Verifica se o objeto está presente no Optional
        if (optionalPilaCoinJson.isPresent() && optionalUsuario.isPresent()) {
           PilaCoin pila = optionalPilaCoinJson.get();
            Usuario usuario = optionalUsuario.get();

            System.out.println("Iniciando transferencia do pila:"  + "\nPara o usuário: " + usuario.getNome());
                Chaves chaves = new Chaves();
                publicKey = chaves.getPublicKey();

            TransferenciaPila transferenciaPila = TransferenciaPila.builder().
                    chaveUsuarioOrigem(pila.getChaveCriador()).
                    chaveUsuarioDestino(usuario.getChavePublica()).
                    nomeUsuarioOrigem(pila.getNomeCriador()).
                    nomeUsuarioDestino(usuario.getNome()).
                    noncePila(pila.getNonce()).
                    dataTransacao(new Date(System.currentTimeMillis())).
                    build();
            ObjectMapper om = new ObjectMapper();
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String json = om.writeValueAsString(transferenciaPila);

            Cipher cipher = Cipher.getInstance("RSA");

            privateKey = chaves.getPrivateKey();
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] assinatura = cipher.doFinal(md.digest(json.getBytes(StandardCharsets.UTF_8)));

            transferenciaPila.setAssinatura(assinatura);

            System.out.println("Tranferindo o pila...");

            System.out.println(om.writeValueAsString(transferenciaPila));
            requisisaoService.eviarRequisisao("transferir-pila", om.writeValueAsString(transferenciaPila));
            return true;
        } else {
           System.out.println("Pila ou usuário não encontrado!");
        }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
