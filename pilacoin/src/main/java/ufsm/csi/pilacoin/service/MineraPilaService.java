package ufsm.csi.pilacoin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import ufsm.csi.pilacoin.model.PilaCoin;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Random;

@Service
public class MineraPilaService implements Runnable{
    private boolean primeiroPila = false;
    private final RequisisaoService requisisaoService;
    
    public static BigInteger pilaMinerado;

    //public PublicKey chavePublica = "[44, -125, -90, 44, 11, 4, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0, 48, -127, -119, 2, -127, -127, 0, -84, 114, 108, 116, 6, -68, -105, 113, 49, 15, 106, -6, 45, -57, -41, -24, -80, 18, -46, 95, 29, 31, 45, -47, 81, 107, 21, -30, -16, -26, 89, -66, 109, -49, -82, -116, -86, 9, -46, 44, -7, -65, 91, -21, -35, 37, 23, 21, 55, 55, -125, -85, -117, -61, 112, 32, -80, -92, -104, 30, 76, -97, 127, 77, -103, 119, -44, 72, 16, 100, 116, 39, 65, -17, 88, -108, -24, -71, -114, 29, 64, -128, 74, 38, -34, 54, -106, 77, 60, -18, 5, 69, -90, -107, 87, 8, -119, -126, -60, 59, -73, 20, 27, 74, 33, 73, -117, -1, -74, 124, 97, -53, 1, 85, -51, -1, 71, -125, -34, 72, -17, -11, 127, -51, -82, -92, -87, -83, 2, 3, 1, 0, 1]";
    public static PublicKey publicKey;
    public static PrivateKey privateKey;

    public MineraPilaService(RequisisaoService requisisaoService) {
        this.requisisaoService = requisisaoService;
    }

    public void minerar(int threads) {

        if(!primeiroPila) {
            this.requisisaoService.eviarRequisisao("pila-minerado", "");
            this.primeiroPila = true;
        }
        for(int i = 0; i < threads; i++) {
            new Thread(new MineraPilaService(requisisaoService)).start();
        }
    }

    @Override
    @SneakyThrows
    public void run() {
        // Gerar chave publica
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        publicKey = keyPairGenerator.generateKeyPair().getPublic();
        privateKey = keyPairGenerator.generateKeyPair().getPrivate();
        BigInteger hash;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String json = "";
        // Gera json com as minhas informações

        PilaCoin pilaCoin = PilaCoin.builder()
                .dataCriacao(new Date(System.currentTimeMillis()))
                .chaveCriador(publicKey.toString().getBytes(StandardCharsets.UTF_8))
                .nomeCriador("joao vitor")
                .build();
        int vezes = 0;

        while(true) {
            Random random = new Random();
            byte[] byteArray = new byte[256 / 8];
            random.nextBytes(byteArray);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            pilaCoin.setNonce(new BigInteger(md.digest(byteArray)).abs().toString());
            pilaCoin.setDataCriacao(new Date(System.currentTimeMillis()));
            json = ow.writeValueAsString(pilaCoin);
            hash = new BigInteger(md.digest(json.getBytes(StandardCharsets.UTF_8))).abs();
            vezes++;

            // Printa se conseguir minerar um PilaCoin e quantas vezes teve que tentar atá encontrar

            if(hash.compareTo(DificuldadeService.dificuldadeAtual) < 0) {
                this.requisisaoService.eviarRequisisao("pila-minerado", json);

                System.out.println("=======SUCESSO==========");
                System.out.println( "Minerei " + vezes + " vezes e encontrei 1 Pila");
                System.out.println(json);

                vezes = 0;
            }
        }
    }

}
