package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPillarRequestDTO extends BaseRequestDTO {

    private Integer entidadeId;
    private Integer cicloId;
    private Integer pilarId;
    private Integer novoPeso;
    private Integer pesoAnterior;
    private String motivacao;

}
