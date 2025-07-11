package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.dto.EnumDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CyclePillarResponseDTO extends BaseResponseDTO {

    private PillarResponseDTO pillar;
    private EnumDTO averageType;
    private Integer standardWeight;
    private UserResponseDTO author;

}
