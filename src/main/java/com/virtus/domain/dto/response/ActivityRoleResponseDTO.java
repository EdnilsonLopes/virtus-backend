package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityRoleResponseDTO extends BaseResponseDTO {

    private RoleResponseDTO role;

}
