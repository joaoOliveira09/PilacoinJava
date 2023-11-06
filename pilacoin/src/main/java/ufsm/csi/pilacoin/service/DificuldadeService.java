package ufsm.csi.pilacoin.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ufsm.csi.pilacoin.model.Dificuldade;

import java.math.BigInteger;

@Service
public class DificuldadeService {
    private MineraPilaService mineraPilaService;
    private MineraBlocoService mineraBlocoService;
    private boolean verificaTh = false;
    public static BigInteger dificuldadeAtual;
    private BigInteger novaDificuldade;
    private boolean verificaDif = true;
    public static final int numThreads = 4;

    public DificuldadeService(MineraPilaService mineraPilaService, MineraBlocoService mineraBlocoService) {
       this.mineraPilaService = mineraPilaService;
       this.mineraBlocoService = mineraBlocoService;
    }
    // Pega a dificuldade do Sor 
    @SneakyThrows
    @RabbitListener(queues = {"${queue.dificuldade}"})
    public void getDificuldade(@Payload String difSor) {
        ObjectMapper objectMapper = new ObjectMapper();
        Dificuldade dif = objectMapper.readValue(difSor, Dificuldade.class);
        dificuldadeAtual = new BigInteger(dif.getDificuldade(), 16);
        //Testa se a dificuldade pega mudou

       if(!dificuldadeAtual.equals(this.novaDificuldade) && !verificaDif) {
            System.out.println("===============================================");
            System.out.println("Dificuldade Do Sor Mudou = " + dificuldadeAtual);
            System.out.println("===============================================");
        }
        // Printa a dificuldade pega 

        if(verificaDif) {
            System.out.println("===============================================");
            System.out.println("Dificuldade Do Sor = " + dificuldadeAtual);
            System.out.println("===============================================");
            verificaDif = false;
        }
        novaDificuldade = dificuldadeAtual;
        
        if(!verificaTh) {
             this.mineraPilaService.minerar(numThreads);
            //this.mineraBlocoService.minerar(numThreads);
            this.verificaTh = true;
        }
    }

}
