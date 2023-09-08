package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.entity.ActivityRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
