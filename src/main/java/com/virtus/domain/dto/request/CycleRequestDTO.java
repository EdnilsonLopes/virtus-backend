package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CycleRequestDTO extends BaseRequestDTO {

    private Integer ordination;
    private String name;
    private String description;
    private String reference;
    private List<CyclePillarRequestDTO> cyclePillars;

}
