package com.virtus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDTO {

    private UserResponseDTO member;
    private LocalDate startsAt;
    private LocalDate endsAt;
}
