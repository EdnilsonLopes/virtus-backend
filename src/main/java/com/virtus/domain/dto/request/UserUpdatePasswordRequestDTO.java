package com.virtus.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdatePasswordRequestDTO {

    private Integer userId;
    private String password;
    private String repeatedPassword;

}
