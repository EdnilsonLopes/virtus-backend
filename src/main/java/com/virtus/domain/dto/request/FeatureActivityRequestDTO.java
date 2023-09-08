package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.domain.dto.response.FeatureResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeatureActivityRequestDTO extends BaseRequestDTO {

    private FeatureResponseDTO feature;

}
