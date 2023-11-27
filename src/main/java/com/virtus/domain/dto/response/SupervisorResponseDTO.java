package com.virtus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupervisorResponseDTO {

    private Integer userId;
    private String name;
    private String role;

}
