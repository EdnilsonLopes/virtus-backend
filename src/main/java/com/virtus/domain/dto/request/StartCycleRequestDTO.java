package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.domain.dto.response.CycleResponseDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import com.virtus.domain.entity.EntityVirtus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class StartCycleRequestDTO {

    private CycleRequestDTO cycle;
    private LocalDate startsAt;
    private LocalDate endsAt;
    private List<EntityVirtusResponseDTO> entities;

}
