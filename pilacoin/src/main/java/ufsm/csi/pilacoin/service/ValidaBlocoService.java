package ufsm.csi.pilacoin.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import lombok.SneakyThrows;
import ufsm.csi.pilacoin.Chaves;
import ufsm.csi.pilacoin.model.Bloco;
import ufsm.csi.pilacoin.model.ValidacaoBloco;

@Service
public class ValidaBlocoService {

    public static final int numeroDeThreads = 4;

    //private boolean minerar = false;
    // private final ObjectReader objectReader = new ObjectMapper().reader();
    //private final DificuldadeService dificuldadeService;
    private final RequisisaoService requisisaoService;
    public static PrivateKey privateKey;
    public static PublicKey publicKey;
    // private final ObjectWriter objectWriter = new ObjectMapper().writer();

    public ValidaBlocoService(DificuldadeService dificuldadeService, RequisisaoService requisisaoService) {
        //this.dificuldadeService = dificuldadeService;
        this.requisisaoService = requisisaoService;
    }

    @SneakyThrows
    @RabbitListener(queues = "descobre-bloco")
    public void descobreBloco(@Payload String blocoJson) throws JsonProcessingException, NoSuchAlgorithmException {
        System.out.println("Descobriu um bloco!");
        System.out.println(blocoJson);
        ObjectMapper om = new ObjectMapper();
        Bloco bloco = om.readValue(blocoJson, Bloco.class);
        String nonceAnterior = (bloco.getNonce() != null)?bloco.getNonce():"nulo";
        System.out.println("Nonce anterior: "+ nonceAnterior);
        BigInteger hash;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        Chaves chaves = new Chaves();
                    publicKey = chaves.getPublicKey();
                    privateKey = chaves.getPrivateKey();
        bloco.setChaveUsuarioMinerador(publicKey.toString().getBytes());
        boolean loop = true;
        while(loop){
            Random rnd = new Random();
            byte[] bytes = new byte[256/8];
            rnd.nextBytes(bytes);
            String nonce = new BigInteger(bytes).abs().toString();
            bloco.setNonce(nonce);
            hash = new BigInteger(md.digest(om.writeValueAsString(bloco).getBytes(StandardCharsets.UTF_8))).abs();
            if (hash.compareTo(DificuldadeService.dificuldadeAtual) < 0){
                requisisaoService.eviarRequisisao("bloco-minerado", om.writeValueAsString(bloco));
                System.out.println(hash);
                loop = false;
            }
        }
        System.out.println("bloco minerado!");
        System.out.println(om.writeValueAsString(bloco));

    }


     @RabbitListener(queues = "bloco-minerado")
    public void validaBloco(@Payload String blockStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, JsonProcessingException, IllegalBlockSizeException, BadPaddingException {
        System.out.println("/////////////".repeat(4));
        System.out.println("Validando bloco!");
        ObjectMapper om = new ObjectMapper();
        Bloco bloco;
        try {
            bloco = om.readValue(blockStr, Bloco.class);
        } catch (JsonProcessingException e) {
            requisisaoService.eviarRequisisao("bloco-minerado", blockStr);
            System.out.println("Erro conversão");
            return;
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        BigInteger hash = new BigInteger(md.digest(blockStr.getBytes(StandardCharsets.UTF_8))).abs();
        System.out.println(hash);
        if(hash.compareTo(DificuldadeService.dificuldadeAtual) < 0){
            md.reset();//reseta o MessageDigest para usar dnv
                    byte[] hashh = md.digest(blockStr.getBytes(StandardCharsets.UTF_8));
                    Cipher cipher = Cipher.getInstance("RSA");
                    Chaves chaves = new Chaves();
                    publicKey = chaves.getPublicKey();
                    privateKey = chaves.getPrivateKey();

                    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
                    String hashStr = om.writeValueAsString(hashh);
                    byte[] assinatura = md.digest(hashStr.getBytes(StandardCharsets.UTF_8));//assinatura

            ValidacaoBloco vbj = ValidacaoBloco.builder().
                    assinaturaBloco(cipher.doFinal(assinatura)).
                    chavePublicaValidador(publicKey.toString().getBytes(StandardCharsets.UTF_8)).
                    nomeValidador("Joao Vitor").build();
            try {
                requisisaoService.eviarRequisisao("bloco-validado", om.writeValueAsString(vbj));
                System.out.println("Valido! :)");
            } catch (JsonProcessingException e) {
                requisisaoService.eviarRequisisao("bloco-minerado", blockStr);
                System.out.println("Erro conversão");
                return;
            }
        } else {
            requisisaoService.eviarRequisisao("bloco-minerado", blockStr);
            System.out.println("Não validou :(");
        }
        System.out.println("//////////////".repeat(4));
    }


    
}
