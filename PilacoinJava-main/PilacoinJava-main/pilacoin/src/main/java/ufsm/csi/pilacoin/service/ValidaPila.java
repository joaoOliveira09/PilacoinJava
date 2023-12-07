 package ufsm.csi.pilacoin.service;



 import com.fasterxml.jackson.databind.ObjectMapper;
 import lombok.SneakyThrows;
 import ufsm.csi.pilacoin.model.Dificuldade;
 import ufsm.csi.pilacoin.model.PilaCoin;
 import ufsm.csi.pilacoin.model.ValidacaoPila;

 import org.springframework.amqp.rabbit.annotation.RabbitListener;
 import org.springframework.amqp.rabbit.core.RabbitTemplate;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.messaging.handler.annotation.Payload;
 import org.springframework.stereotype.Service;

 import javax.crypto.Cipher;
 import java.math.BigInteger;
 import java.nio.charset.StandardCharsets;
 import java.security.MessageDigest;
 import java.security.PrivateKey;
 import java.security.Signature;
 import java.util.ArrayList;

 @Service
 public class ValidaPila {
     @Autowired
     public ValidaPila(RabbitTemplate rabbitTemplate) {
         this.rabbitTemplate = rabbitTemplate;
     }

     private final RabbitTemplate rabbitTemplate;
     public static PrivateKey privateKey;
     private static ArrayList<String> pilaIgnroe = new ArrayList<>();
     public  String Logs;

     @SneakyThrows
     @RabbitListener(queues = "pila-minerado")
     public void getMinerados(@Payload String pilaStr) {
         boolean fim = true;
         for(String pila: pilaIgnroe){
             if (pila.equals(pilaStr)){
                 fim = false;
                 break;
             }
         }
         pilaIgnroe.add(pilaStr);
         if (fim){
             //System.out.println("Pila dos outros: "+pilaStr);
             ObjectMapper ob = new ObjectMapper();
             PilaCoin pilacoin = ob.readValue(pilaStr, PilaCoin.class);
             if(pilacoin.getNomeCriador().equals("")){
                 rabbitTemplate.convertAndSend("pila-minerado",pilaStr);//devolve pq n é meu
             } else {
                 //System.out.println("Não é meu");
                 MessageDigest md = MessageDigest.getInstance("SHA-256");
                 BigInteger hash = new BigInteger(md.digest(pilaStr.getBytes(StandardCharsets.UTF_8))).abs();
                 while(DificuldadeService.dificuldadeAtual == null){}//garatnir q n vai tentar comparar antes de receber a dificuldade
                 if(hash.compareTo(DificuldadeService.dificuldadeAtual) < 0){
                     System.out.println("Validando pila do(a): "+pilacoin.getNomeCriador());
                     Logs = "Validando Pila!";
                     md.reset();
                     Cipher cipher = Cipher.getInstance("RSA");
                     privateKey = MineraPilaService.privateKey;
                     cipher.init(Cipher.ENCRYPT_MODE, privateKey);

                     byte[] assinatura = cipher.doFinal(md.digest(pilaStr.getBytes(StandardCharsets.UTF_8)));//assinatura
                     ValidacaoPila validacaoPila = ValidacaoPila.builder().pilaCoinJson(pilacoin).
                             assinaturaPilaCoin(assinatura).
                             nomeValidador("Joao Vitor Oliveira").
                             chavePublicaValidador(MineraPilaService.publicKey.getEncoded()).build();

                     rabbitTemplate.convertAndSend("pila-validado", ob.writeValueAsString(validacaoPila));

                     String jsonValidado = ob.writeValueAsString(validacaoPila);
                     System.out.println("Minha Assinatura: "+jsonValidado);
                     System.out.println("Valido!");

                 } else {
 //                    System.out.println("Não Validou!");
                     rabbitTemplate.convertAndSend("pila-minerado", pilaStr);
                 }
             }
         }
     }

 }