package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComponentRequestDTO extends BaseRequestDTO {

    private String name;
    private String description;
    private String reference;
    private boolean pga;
    private Integer ordination;
    private List<ComponentElementRequestDTO> componentElements;
    private List<ComponentGradeTypeRequestDTO> componentGradeTypes;
    private List<ComponentIndicatorRequestDTO> componentIndicators;

}
