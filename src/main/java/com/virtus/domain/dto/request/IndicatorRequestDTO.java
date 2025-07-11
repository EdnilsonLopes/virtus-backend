package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndicatorRequestDTO extends BaseRequestDTO {

    private String indicatorAcronym;
    private String indicatorName;
    private String indicatorDescription;

}
