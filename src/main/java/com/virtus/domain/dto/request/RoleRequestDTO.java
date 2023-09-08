package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.domain.entity.FeatureRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleRequestDTO extends BaseRequestDTO {

    private String name;
    private String description;
    private List<FeatureRoleRequestDTO> features;

}
