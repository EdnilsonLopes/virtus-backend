package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.dto.request.FeatureRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeatureRoleResponseDTO extends BaseResponseDTO {

    private FeatureResponseDTO feature;

}
