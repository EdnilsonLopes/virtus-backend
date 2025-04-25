package com.virtus.domain.dto.request;

import java.time.LocalDate;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.domain.dto.response.UserResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDTO extends BaseRequestDTO {

    private Integer id;
    private UserResponseDTO user;
    private LocalDate startsAt;
    private LocalDate endsAt;

}
