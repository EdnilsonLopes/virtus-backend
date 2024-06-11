package com.virtus.domain.dto.response;

import com.virtus.domain.dto.AuditorDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductComponentResponseDTO {

    private Integer id;
    private ComponentResponseDTO component;
    private PillarResponseDTO pillar;
    private CycleEntityResponseDTO cycle;
    private EntityVirtusResponseDTO entity;
    private SupervisorResponseDTO supervisor;
    private AuditorDTO auditor;
    private LocalDate startsAt;
    private LocalDate endsAt;

}
