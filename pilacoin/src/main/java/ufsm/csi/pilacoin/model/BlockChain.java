package ufsm.csi.pilacoin.model;

import lombok.Data;

import java.util.List;

@Data
public class BlockChain {
    private Long numeroBloco;
    private String nonce;
    private byte[] chaveUsuarioMinerador;
    private List<Object> transacoes;
}
