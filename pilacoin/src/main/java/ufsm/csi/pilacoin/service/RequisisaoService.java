package ufsm.csi.pilacoin.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;

//Service reaproveitado para enviar todas a requisições para o server do sor
@Service
public class RequisisaoService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void eviarRequisisao(String routingKey, String objeto) {
        this.rabbitTemplate.convertAndSend(routingKey, objeto);
    }

    //metodo responsável por receber as mensagens enviadas pelo server do sor
       @RabbitListener(queues = {"joao_vitor"})
      public void recebeMensagem(@Payload Message message) {
          String responseMessage = new String(message.getBody());
           System.out.println(responseMessage);
       }
}
