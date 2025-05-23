package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserResponseDTO extends BaseResponseDTO {

    private Integer id;
    private String name;
    private String username;
    private String email;
    private String mobile;
    private String password;
    private RoleResponseDTO role;

}
