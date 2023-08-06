package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.entity.Element;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentElementResponseDTO extends BaseResponseDTO {

    private ElementResponseDTO element;
    private GradeTypeResponseDTO gradeType;
    private Integer standardWeight;
    private UserResponseDTO author;

}
