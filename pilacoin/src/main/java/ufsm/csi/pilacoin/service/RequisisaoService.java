package ufsm.csi.pilacoin.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Service reaproveitado para enviar todas a requisições para o server do sor
@Service
public class RequisisaoService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void eviarRequisisao(String routingKey, String objeto) {
        this.rabbitTemplate.convertAndSend(routingKey, objeto);
    }
}
