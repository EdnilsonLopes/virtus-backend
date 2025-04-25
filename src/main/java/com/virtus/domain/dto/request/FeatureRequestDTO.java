package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeatureRequestDTO extends BaseRequestDTO {

    private String name;
    private String code;
    private String description;

}
