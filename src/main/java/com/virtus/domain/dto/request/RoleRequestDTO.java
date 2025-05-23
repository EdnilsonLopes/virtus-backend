package com.virtus.domain.dto.request;

import java.util.List;

import com.virtus.common.domain.dto.BaseRequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequestDTO extends BaseRequestDTO {

    private Integer id;
    private String name;
    private String description;
    private List<FeatureRoleRequestDTO> features;

}
