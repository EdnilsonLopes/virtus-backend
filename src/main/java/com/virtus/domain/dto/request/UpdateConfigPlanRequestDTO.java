package com.virtus.domain.dto.request;

import com.virtus.domain.dto.response.PlanResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateConfigPlanRequestDTO {

    private Integer componentId;
    private Integer cycleId;
    private Integer entityId;
    private Integer pillarId;
    private List<PlanResponseDTO> plans;

}
