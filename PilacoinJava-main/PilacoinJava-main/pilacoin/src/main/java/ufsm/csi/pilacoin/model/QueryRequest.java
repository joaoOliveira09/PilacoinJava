package ufsm.csi.pilacoin.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonPropertyOrder(alphabetic = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryRequest {
    private long idQuery;
    private String nomeUsuario;
    private String status;
    private String usuarioMinerador;
    private QueryType tipoQuery;
    private String nonce;
    private Long idBloco;

    public enum QueryType{
        PILA,
        BLOCO,
        USUARIOS
    }

}
