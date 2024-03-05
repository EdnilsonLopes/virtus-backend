package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO extends BaseResponseDTO {

    private Integer id;
    private UserResponseDTO user;
    private LocalDate startsAt;
    private LocalDate endsAt;
}
