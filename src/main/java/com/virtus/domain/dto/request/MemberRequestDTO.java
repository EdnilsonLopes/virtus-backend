package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.dto.response.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberRequestDTO extends BaseRequestDTO {

    private Integer id;
    private UserResponseDTO user;
    private LocalDate startsAt;
    private LocalDate endsAt;

}
