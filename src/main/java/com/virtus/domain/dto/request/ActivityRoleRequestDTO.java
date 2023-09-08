package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.domain.dto.response.ActivityResponseDTO;
import com.virtus.domain.dto.response.RoleResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityRoleRequestDTO extends BaseRequestDTO {

    private RoleResponseDTO role;

}
