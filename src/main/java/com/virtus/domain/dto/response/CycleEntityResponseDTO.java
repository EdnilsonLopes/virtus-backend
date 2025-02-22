package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.dto.EnumDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CycleEntityResponseDTO extends BaseResponseDTO {

    private CycleResponseDTO cycle;
    private EntityVirtusResponseDTO entity;
    private EnumDTO averageType;
    private UserResponseDTO supervisor;
    private LocalDate startsAt;
    private LocalDate endsAt;

}
