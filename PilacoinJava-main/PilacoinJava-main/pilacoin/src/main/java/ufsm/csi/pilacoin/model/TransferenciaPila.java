package ufsm.csi.pilacoin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferenciaPila {

    private byte[] chaveUsuarioOrigem;
    private byte[]chaveUsuarioDestino;
    private String nomeUsuarioOrigem;
    private String nomeUsuarioDestino;
    private byte[] assinatura;
    private String noncePila;
    private Date dataTransacao;
    private Long id;
    private String status;
}

