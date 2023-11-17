package ufsm.csi.pilacoin.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class Transacoes {
    
     private String chaveUsuarioOrigem;
     private String chaveUsuarioDestino;
     private String assinatura;
     private String noncePila;
     private Date dataTransacao;
     private Long id;
     private String status;
}
