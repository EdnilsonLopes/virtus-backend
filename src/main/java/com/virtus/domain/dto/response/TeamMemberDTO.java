package com.virtus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDTO {

    private Integer id;
    private MemberResponseDTO member;
    private LocalDate startsAt;
    private LocalDate endsAt;
}
