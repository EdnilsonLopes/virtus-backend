package com.virtus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPlanHistoryDTO {

    private Long idProdutoPlanoHistorico;
    private Long idEntidade;
    private Long idCiclo;
    private Long idPilar;
    private Long idComponente;
    private Long idPlano;
    private Double nota;
    private Double notaAnterior;
    private String tipoAlteracao;
    private String motivacao;
    private Long idAuthor;
    private String authorName;
    private String alteradoEm;
}
