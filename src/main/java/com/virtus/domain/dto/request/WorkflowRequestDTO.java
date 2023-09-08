package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.domain.dto.EnumDTO;
import com.virtus.domain.enums.EntityInSystem;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class WorkflowRequestDTO extends BaseRequestDTO {

    private String name;
    private String description;
    private EnumDTO<EntityInSystem> entityType;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private List<ActivityRequestDTO> activities;

}
