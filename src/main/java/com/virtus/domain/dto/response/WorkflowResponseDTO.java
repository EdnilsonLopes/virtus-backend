package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.dto.EnumDTO;
import com.virtus.domain.enums.EntityInSystem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkflowResponseDTO extends BaseResponseDTO {

    private String name;
    private String description;
    private EnumDTO entityType;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<ActivityResponseDTO> activities = new ArrayList<>();
    private ActionResponseDTO action;


}
