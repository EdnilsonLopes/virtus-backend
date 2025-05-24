package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductElementRequestDTO extends BaseRequestDTO {

    private Integer entidadeId;
    private Integer cicloId;
    private Integer pilarId;
    private Integer planoId;
    private Integer componenteId;
    private Integer tipoNotaId;
    private Integer elementoId;
    private Integer nota;
    private String motivacao;

}
