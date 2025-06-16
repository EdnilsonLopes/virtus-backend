package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCycleRequestDTO extends BaseRequestDTO {

    private Integer entidadeId;
    private Integer cicloId;
    private String analise;

}
