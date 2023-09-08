package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfficeResponseDTO extends BaseResponseDTO {

    private String name;
    private String abbreviation;
    private String description;
    private UserResponseDTO boss;

}
