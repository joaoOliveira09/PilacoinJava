package ufsm.csi.pilacoin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.amqp.core.Message;
import ufsm.csi.pilacoin.model.*;
import ufsm.csi.pilacoin.repository.PilacoinRepository;
import ufsm.csi.pilacoin.repository.UsuarioRepository;

import javax.management.Query;
import java.math.BigInteger;

//Service reaproveitado para enviar todas a requisições para o server do sor
@Service
public class RequisisaoService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PilacoinRepository pilacoinRepository;

    public void eviarRequisisao(String routingKey, String objeto) {
        this.rabbitTemplate.convertAndSend(routingKey, objeto);
    }

    //@Scheduled(initialDelay = 5000,fixedDelay = 5000)
    @SneakyThrows
    public void pegaUsuario(){
        QueryRequest query = QueryRequest.builder().idQuery(69).
                nomeUsuario("Joao Vitor Oliveira").
                tipoQuery(QueryRequest.QueryType.USUARIOS).build();
        ObjectMapper om = new ObjectMapper();
        eviarRequisisao("query",om.writeValueAsString(query));
    }

    //@Scheduled(initialDelay = 5000,fixedDelay = 5000)
    @SneakyThrows
    public void pegaPila(){
        QueryRequest query = QueryRequest.builder().idQuery(70).
                nomeUsuario("Joao Vitor Oliveira").
                tipoQuery(QueryRequest.QueryType.PILA).
                usuarioMinerador("Joao Vitor Oliveira").
                build();
        ObjectMapper om = new ObjectMapper();
        eviarRequisisao("query",om.writeValueAsString(query));
    }


    @SneakyThrows
    //@RabbitListener(queues = {"joao_vitor_oliveira-query"})
    public void respostas(@Payload String strresponse){
        ObjectMapper om = new ObjectMapper();
        QueryResponse queryResponse = om.readValue(strresponse,QueryResponse.class);
        //System.out.println(strresponse);
        if(queryResponse.getIdQuery() == 69){
            for (Usuario u: queryResponse.getUsuariosResult()){
               if (u != null){
                   //this.usuarioRepository.save(u);
                   System.out.println(u.getNome());
                }

            }
        }
        if(queryResponse.getIdQuery() == 70) {

                for (PilaCoin p : queryResponse.getPilasResult()) {
                    if (p != null) {
                        if (p.getStatus().equals("VALIDO")) {
                            this.pilacoinRepository.save(p);

                            System.out.println(p.getNonce());
                        }

                    }

                }
            }
        }

    @RabbitListener(queues ={ "report" })
    public void getReportUser(@Payload String message){
        try {
            System.out.println(message);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


       //@RabbitListener(queues = {"joao_vitor_oliveira"})
      public void recebeMensagem(@Payload Message message) {
          String responseMessage = new String(message.getBody());
           System.out.println(responseMessage);
       }

//    @RabbitListener(queues = {"Joao Vitor Oliveira-pila-validado"})
//    public void recebeValidados(@Payload PilaCoin pilaCoin) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        pilaCoin.setStatus("validado");
//        this.pilacoinService.savePila(pilaCoin);
//        System.out.println("cheguei aqui!!!!");
//    }

}
