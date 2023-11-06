package ufsm.csi.pilacoin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import ufsm.csi.pilacoin.Chaves;
import ufsm.csi.pilacoin.model.PilaCoin;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
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
        // Codigo responsável pela chaves publicas em arquivo
        // Chaves chaves = new Chaves();
        //     chaves.salvaChavesArquivo("publicKey.dat", "privateKey.dat");

        //     Chaves loadedChaves = new Chaves();
        //     loadedChaves.getChavesArquivo("publicKey.dat", "privateKey.dat");
        //     publicKey = loadedChaves.getPublicKey();
        // privateKey = loadedChaves.getPrivateKey();


        Chaves chaves = new Chaves();
        publicKey = chaves.getPublicKey();
        privateKey = chaves.getPrivateKey();

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
