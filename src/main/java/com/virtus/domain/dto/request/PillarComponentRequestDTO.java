package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.domain.dto.EnumDTO;
import com.virtus.domain.entity.Component;
import com.virtus.domain.enums.AverageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PillarComponentRequestDTO extends BaseRequestDTO {

    private Component component;
    private Integer standardWeight;
    private EnumDTO<AverageType> averageType;
    private String probeFile;

}
