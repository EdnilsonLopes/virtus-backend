package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.dto.EnumDTO;
import com.virtus.domain.dto.response.CycleResponseDTO;
import com.virtus.domain.entity.User;
import com.virtus.domain.enums.AverageType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CycleEntityRequestDTO extends BaseResponseDTO {

    private CycleResponseDTO cycle;
    private EnumDTO<AverageType> averageType;
    private User supervisor;
    private LocalDate startsAt;
    private LocalDate endsAt;

}
