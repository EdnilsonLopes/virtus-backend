package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentGradeTypeResponseDTO extends BaseResponseDTO {

    private GradeTypeResponseDTO gradeType;
    private Integer standardWeight;
    private UserResponseDTO author;

}
