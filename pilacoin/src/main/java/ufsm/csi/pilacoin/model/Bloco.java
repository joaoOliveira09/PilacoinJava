package ufsm.csi.pilacoin.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
public class Bloco {
    private Long numeroBloco;
    private String nonce;
    private BigInteger nonceBlocoAnterior;
    private byte[] chaveUsuarioMinerador;
    private List<Transacoes> transacoes;

    //private List<Transaction> transacoes;
}
