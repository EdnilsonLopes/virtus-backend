package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentIndicatorRequestDTO extends BaseRequestDTO {

    private IndicatorRequestDTO indicator;
    private Integer standardWeight;

}
