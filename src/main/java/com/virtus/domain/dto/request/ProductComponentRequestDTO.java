package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductComponentRequestDTO extends BaseRequestDTO {

    private Integer entidadeId;
    private Integer cicloId;
    private Integer pilarId;
    private Integer componenteId;
    private Integer novoAuditorId;
    private Integer auditorAnteriorId;
    private Integer novoIniciarEm;
    private Integer iniciarEmAnterior;
    private Integer novoTerminarEm;
    private Integer terminarEmAnterior;
    private String motivacao;
}
