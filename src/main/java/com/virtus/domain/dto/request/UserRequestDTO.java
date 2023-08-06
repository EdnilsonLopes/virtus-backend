package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO extends BaseRequestDTO {

    private String name;
    private String username;
    private String password;
    private String role;

}
