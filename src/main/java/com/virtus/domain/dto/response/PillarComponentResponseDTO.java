package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.dto.EnumDTO;
import lombok.Getter;
import lombok.Setter;
import com.virtus.domain.enums.AverageType;

@Getter
@Setter
public class PillarComponentResponseDTO extends BaseResponseDTO {

    private ComponentResponseDTO component;
    private Integer standardWeight;
    private EnumDTO<AverageType> averageType;
    private String probeFile;
    private UserResponseDTO author;

}
