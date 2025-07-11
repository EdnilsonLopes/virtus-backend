package com.virtus.domain.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.virtus.common.domain.dto.BaseResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityResponseDTO extends BaseResponseDTO {

    private ActionResponseDTO action;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Integer expirationTimeDays;
    private ActionResponseDTO expirationAction;
    private List<FeatureActivityResponseDTO> features = new ArrayList<>();
    private List<ActivityRoleResponseDTO> roles = new ArrayList<>();
    private String rolesStr;

}
