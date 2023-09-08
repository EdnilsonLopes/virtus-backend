package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
