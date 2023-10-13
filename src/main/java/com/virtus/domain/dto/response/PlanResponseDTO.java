package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanResponseDTO extends BaseResponseDTO {

    private String reference;
    private String name;
    private String description;
    private String cnpb;
    private String legislation;
    private String situation;
    private Double guaranteeResource;
    private String modality;
}
