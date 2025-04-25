package com.virtus.domain.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import com.virtus.common.domain.dto.BaseRequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityRequestDTO extends BaseRequestDTO {

    private Integer actionId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Integer expirationTimeDays;
    private Integer expirationActionId;
    private List<FeatureActivityRequestDTO> features;
    private List<ActivityRoleRequestDTO> roles;

}
