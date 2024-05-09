package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistributeActivitiesResponseDTO extends BaseResponseDTO {

    private String code;
    private Integer entityId;
    private String name;
    private String acronym;

}
