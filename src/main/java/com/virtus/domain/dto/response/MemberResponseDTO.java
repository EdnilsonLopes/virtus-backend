package com.virtus.domain.dto.response;

import java.time.LocalDate;

import com.virtus.common.domain.dto.BaseResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
