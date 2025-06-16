package com.virtus.domain.dto.request;

import java.time.LocalDate;

import com.virtus.common.domain.dto.BaseRequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductElementItemRequestDTO extends BaseRequestDTO {

    private Integer entidadeId;
    private Integer cicloId;
    private Integer pilarId;
    private Integer componenteId;
    private Integer planoId;
    private Integer tipoNotaId;
    private Integer elementoId;
    private Integer itemId;
    private Integer novoAuditorId;
    private Integer auditorAnteriorId;
    private LocalDate iniciaEm;
    private LocalDate iniciaEmAnterior;
    private LocalDate terminaEm;
    private LocalDate terminaEmAnterior;
    private String tipoData;
    private String motivacao;
    private String analise;
}
