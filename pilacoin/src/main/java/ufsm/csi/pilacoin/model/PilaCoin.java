package ufsm.csi.pilacoin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonPropertyOrder({"dataCriacao", "chaveCriador", "nomeCriador", "nonce"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_pilacoin")
public class PilaCoin implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    private byte[] chaveCriador;
    private String nomeCriador;
    private String status;
    private String nonce;

    @Override
    public PilaCoin clone() {
       try {
          PilaCoin clone = (PilaCoin) super.clone();
          return clone;
       } catch (CloneNotSupportedException e) {
          throw new AssertionError() ;
       }
    }
}
