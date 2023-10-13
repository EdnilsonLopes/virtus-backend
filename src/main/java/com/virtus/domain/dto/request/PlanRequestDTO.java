package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanRequestDTO extends BaseRequestDTO {

    private String reference;
    private String name;
    private String description;
    private String cnpb;
    private String legislation;
    private String situation;
    private Double guaranteeResource;
    private String modality;
}
