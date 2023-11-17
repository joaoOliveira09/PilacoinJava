package ufsm.csi.pilacoin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;
import ufsm.csi.pilacoin.model.Dificuldade;
import ufsm.csi.pilacoin.model.PilaCoin;

import java.math.BigInteger;

//Service reaproveitado para enviar todas a requisições para o server do sor
@Service
public class RequisisaoService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private PilacoinService pilacoinService;

    public void eviarRequisisao(String routingKey, String objeto) {
        this.rabbitTemplate.convertAndSend(routingKey, objeto);
    }

//       @RabbitListener(queues = {"Joao_Vitor_Oliveira"})
//      public void recebeMensagem(@Payload Message message) {
//          String responseMessage = new String(message.getBody());
//           System.out.println(responseMessage);
//       }
//
//    @RabbitListener(queues = {"Joao Vitor Oliveira-pila-validado"})
//    public void recebeValidados(@Payload PilaCoin pilaCoin) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        pilaCoin.setStatus("validado");
//        this.pilacoinService.savePila(pilaCoin);
//        System.out.println("cheguei aqui!!!!");
//    }

}
