package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.dto.EnumDTO;
import com.virtus.domain.entity.User;
import com.virtus.domain.enums.AverageType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CycleEntityResponseDTO extends BaseResponseDTO {

    private CycleResponseDTO cycle;
    private EntityVirtusResponseDTO entity;
    private EnumDTO averageType;
    private UserResponseDTO supervisor;
    private LocalDate startsAt;
    private LocalDate endsAt;

}
