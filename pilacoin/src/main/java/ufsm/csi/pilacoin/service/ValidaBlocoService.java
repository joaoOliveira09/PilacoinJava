package ufsm.csi.pilacoin.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cglib.core.Block;
import org.springframework.messaging.handler.annotation.Payload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import lombok.SneakyThrows;
import ufsm.csi.pilacoin.Chaves;
import ufsm.csi.pilacoin.model.Bloco;
import ufsm.csi.pilacoin.model.ValidacaoBloco;

public class ValidaBlocoService {

    public static final int numeroDeThreads = 4;
    private Block blocoDescoberto;
    private boolean minerar = false;
    private final ObjectReader objectReader = new ObjectMapper().reader();
    //private final DificuldadeService dificuldadeService;
    private final RequisisaoService requisisaoService;
    public static PrivateKey privateKey;
    public static PublicKey publicKey;
    private final ObjectWriter objectWriter = new ObjectMapper().writer();

    public ValidaBlocoService(DificuldadeService dificuldadeService, RequisisaoService requisisaoService) {
        //this.dificuldadeService = dificuldadeService;
        this.requisisaoService = requisisaoService;
    }

    @SneakyThrows
    @RabbitListener(queues = {"descobre-bloco"})
    public void getBloco(@Payload String blockStr) {
        blocoDescoberto = this.objectReader.readValue(blockStr, Block.class);
        if(!this.minerar) {
            MineraBlocoService mineraBloco = new MineraBlocoService(null);
            mineraBloco.minerar(numeroDeThreads);
            this.minerar = true;
        }
    }


    @SneakyThrows
    @RabbitListener(queues = {"bloco-minerado"})
    public void ValidaBloco(@Payload String blockStr) {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        BigInteger hash = new BigInteger(md.digest(blockStr.getBytes(StandardCharsets.UTF_8))).abs();
        Bloco bloco = this.objectReader.readValue(blockStr, Bloco.class);

        while(DificuldadeService.dificuldadeAtual == null){}//garatnir q n vai tentar comparar antes de receber a dificuldade

        if(hash.compareTo(DificuldadeService.dificuldadeAtual) < 0) {
            Cipher cipher = Cipher.getInstance("RSA");
            Chaves chaves = new Chaves();
             publicKey = chaves.getPublicKey();
             privateKey = chaves.getPrivateKey();

            // privateKey = MineraBlocoService.privateKey;
            // publicKey = MineraBlocoService.publicKey;

            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] hashByteArr = hash.toString().getBytes(StandardCharsets.UTF_8);
            ValidacaoBloco validacaoBloco = ValidacaoBloco.builder()
                    .nomeValidador("Joao Vitor")
                    .bloco(bloco)
                    .assinaturaBloco(cipher.doFinal(hashByteArr))
                    .chavePublicaValidador(publicKey.toString().getBytes(StandardCharsets.UTF_8))
                    .build();
            String json = this.objectWriter.writeValueAsString(validacaoBloco);
            this.requisisaoService.eviarRequisisao("bloco-validado", json);
        } else this.requisisaoService.eviarRequisisao("bloco-minerado", blockStr);
    }


    
}
