package com.virtus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberResponseDTO {

    private Integer userId;
    private String name;
    private String role;
    private Integer subordinationId;

}
