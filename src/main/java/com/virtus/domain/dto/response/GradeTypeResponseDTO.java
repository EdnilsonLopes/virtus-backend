package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class GradeTypeResponseDTO extends BaseResponseDTO {

    public GradeTypeResponseDTO() {
        //TODO Auto-generated constructor stub
    }
    private Integer ordination;
    private String name;
    private String description;
    private String reference;
    private String letter;
    private String letterColor;
    private UserResponseDTO author;

}
