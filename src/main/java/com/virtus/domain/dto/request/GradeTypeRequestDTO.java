package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeTypeRequestDTO extends BaseRequestDTO {

    private Integer ordination;
    private String name;
    private String description;
    private String reference;
    private String letter;
    private String letterColor;

}
