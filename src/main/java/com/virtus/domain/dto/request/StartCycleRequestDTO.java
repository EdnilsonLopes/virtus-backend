package com.virtus.domain.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.virtus.domain.dto.response.EntityVirtusResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StartCycleRequestDTO {

    private CycleRequestDTO cycle;
    private LocalDate startsAt;
    private LocalDate endsAt;
    private List<EntityVirtusResponseDTO> entities;

}
