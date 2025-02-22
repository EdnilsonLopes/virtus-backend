package com.virtus.common.domain.dto;

import com.virtus.domain.dto.response.StatusResponseDTO;
import com.virtus.domain.dto.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDTO {

    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponseDTO author;
    private StatusResponseDTO status;

}
