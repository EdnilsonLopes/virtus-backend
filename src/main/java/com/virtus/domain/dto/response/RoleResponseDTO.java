package com.virtus.domain.dto.response;

import java.util.List;

import com.virtus.common.domain.dto.BaseResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleResponseDTO extends BaseResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private List<FeatureRoleResponseDTO> features;

}
