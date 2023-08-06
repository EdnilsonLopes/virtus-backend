package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserResponseDTO extends BaseResponseDTO {

    private String name;
    private String username;

}
