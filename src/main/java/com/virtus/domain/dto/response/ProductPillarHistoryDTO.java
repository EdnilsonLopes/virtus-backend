package com.virtus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPillarHistoryDTO {

    private Long idProdutoPilarHistorico;
    private Long idEntidade;
    private Long idCiclo;
    private Long idPilar;
    private String idTipoPontuacao;
    private String metodo;
    private Double peso;
    private Double nota;
    private String tipoAlteracao;
    private String motivacaoPeso;
    private String motivacaoNota;
    private Long idSupervisor;
    private Long idAuditor;
    private Long auditorAnteriorId;
    private Long idAuthor;
    private String authorName;
    private String alteradoEm;

}
