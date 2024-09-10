package com.virtus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryComponentDTO {

    private Long idProdutoComponenteHistorico;
    private Long idEntidade;
    private Long idCiclo;
    private Long idPilar;
    private Long idComponente;
    private String iniciaEm;
    private String iniciaEmAnterior;
    private String terminaEm;
    private String terminaEmAnterior;
    private String config;
    private String configAnterior;
    private Double peso;
    private Integer idTipoPontuacao;
    private Double nota;
    private String tipoAlteracao;
    private Long idAuditor;
    private Long auditorAnteriorId;
    private Long idAuthor;
    private String authorName;
    private String alteradoEm;
    private String motivacao;

}
