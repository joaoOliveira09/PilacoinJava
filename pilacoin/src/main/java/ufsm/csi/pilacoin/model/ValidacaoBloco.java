package ufsm.csi.pilacoin.model;

import org.springframework.cglib.core.Block;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidacaoBloco {
   String nomeValidador;
    byte[] chavePublicaValidador;
    byte[] assinaturaBloco;
    Bloco bloco; 
}
