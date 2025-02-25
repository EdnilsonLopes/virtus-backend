package com.virtus.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluatePlansConsultModel {

    private Integer entidadeId;
    private String entidadeNome;

    private Integer cicloId;
    private String cicloNome;
    private BigDecimal cicloNota;

    private Integer pilarId;
    private String pilarNome;
    private BigDecimal pilarPeso;
    private BigDecimal pilarNota;

    private Integer componenteId;
    private String componenteNome;
    private BigDecimal componentePeso;
    private BigDecimal componenteNota;

    private Integer supervisorId;
    private String supervisorNome;

    private Integer auditorId;

    private Integer tipoNotaId;
    private String tipoNotaLetra;
    private String tipoNotaCorLetra;
    private String tipoNotaNome;
    private BigDecimal tipoNotaPeso;
    private BigDecimal tipoNotaNota;

    private Integer elementoId;
    private String elementoNome;
    private BigDecimal elementoPeso;
    private BigDecimal elementoNota;

    private Integer tipoPontuacaoId;

    private BigDecimal pesoPadraoEC;
    private Integer tipoMediaCPId;
    private BigDecimal pesoPadraoCP;
    private Integer tipoMediaPCId;
    private BigDecimal pesoPadraoPC;

    private Integer itemId;
    private String itemNome;

    private Integer planoId;
    private String cnpb;
    private String recursoGarantidor;
    private String planoModalidade;
    private BigDecimal planoPeso;
    private BigDecimal planoNota;

    private LocalDateTime iniciaEm;
    private LocalDateTime terminaEm;

    private String cStatus;
    private Integer statusId;

    private Integer produtoComponenteId;

    private Boolean periodoPermitido;
    private Boolean periodoCiclo;
}

