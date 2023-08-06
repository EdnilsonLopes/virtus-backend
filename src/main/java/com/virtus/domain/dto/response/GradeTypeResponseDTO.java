package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeTypeResponseDTO extends BaseResponseDTO {

    private Integer ordination;
    private String name;
    private String description;
    private String reference;
    private String letter;
    private String letterColor;
    private UserResponseDTO author;

}
