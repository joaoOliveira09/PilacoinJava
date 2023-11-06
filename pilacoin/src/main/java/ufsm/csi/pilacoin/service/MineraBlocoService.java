package ufsm.csi.pilacoin.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.SneakyThrows;
import ufsm.csi.pilacoin.Chaves;
import ufsm.csi.pilacoin.model.Bloco;
import ufsm.csi.pilacoin.model.PilaCoin;

@Service
public class MineraBlocoService implements Runnable {

private boolean primeiroBloco = false;
    private final RequisisaoService requisisaoService;
    
    public static BigInteger blocoMinerado;
    public static PublicKey publicKey;
    public static PrivateKey privateKey;

    public MineraBlocoService(RequisisaoService requisisaoService) {
        this.requisisaoService = requisisaoService;
    }

    public void minerar(int threads) {

        if(!primeiroBloco) {
            this.requisisaoService.eviarRequisisao("bloco-minerado", "");
            this.primeiroBloco = true;
        }
        for(int i = 0; i < threads; i++) {
            new Thread(new MineraBlocoService(requisisaoService)).start();
        }
    }

    @Override
    @SneakyThrows
    public void run() {
        // Gerar chave publica
        // KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // keyPairGenerator.initialize(1024);
        Chaves chaves = new Chaves();
        publicKey = chaves.getPublicKey();
        privateKey = chaves.getPrivateKey();
        
        BigInteger hash;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String json = "";
        // Gera json com as minhas informações

        Bloco bloco = Bloco.builder().chaveUsuarioMinerador(publicKey.toString().getBytes(StandardCharsets.UTF_8)).build();
        int vezes = 0;

        while(true) {
            Random random = new Random();
            byte[] byteArray = new byte[256 / 8];
            random.nextBytes(byteArray);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            bloco.setNonce(new BigInteger(md.digest(byteArray)).abs().toString());

            String nonce = new BigInteger(byteArray).abs().toString();
            bloco.setNonce(nonce);
            json = ow.writeValueAsString(bloco);
            hash = new BigInteger(md.digest(json.getBytes(StandardCharsets.UTF_8))).abs();
            vezes++;

            // Printa se conseguir minerar um PilaCoin e quantas vezes teve que tentar atá encontrar

            if(hash.compareTo(DificuldadeService.dificuldadeAtual) < 0) {
                this.requisisaoService.eviarRequisisao("bloco-minerado", json);

                System.out.println( "Minerei " + vezes + " vezes e encontrei 1 Bloco");
                System.out.println(json);
                vezes = 0;
            }
        }
    }
    
    
}
