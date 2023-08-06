package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentElementRequestDTO extends BaseRequestDTO {

    private ElementRequestDTO element;
    private GradeTypeRequestDTO gradeType;
    private Integer standardWeight;

}
