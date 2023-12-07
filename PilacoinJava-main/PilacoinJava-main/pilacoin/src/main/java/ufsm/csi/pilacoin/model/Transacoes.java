package ufsm.csi.pilacoin.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Entity
@Table(name = "tb_transacoes")
public class Transacoes {
     @Id
     @GeneratedValue
     private Long id;
     private String chaveUsuarioOrigem;
     private String chaveUsuarioDestino;
     private String assinatura;
     private String noncePila;
     private Date dataTransacao;
     private String status;
}
