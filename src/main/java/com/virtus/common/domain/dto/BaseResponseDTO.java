package com.virtus.common.domain.dto;

import com.virtus.domain.dto.response.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class BaseResponseDTO {

    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
