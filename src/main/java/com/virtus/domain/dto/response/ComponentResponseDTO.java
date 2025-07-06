package com.virtus.domain.dto.response;

import java.util.List;

import com.virtus.common.domain.dto.BaseResponseDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ComponentResponseDTO extends BaseResponseDTO {

    public ComponentResponseDTO() {
        //TODO Auto-generated constructor stub
    }
    private String name;
    private int ordination;
    private String description;
    private String reference;
    private Boolean pga;
    private List<ComponentElementResponseDTO> componentElements;
    private List<ComponentGradeTypeResponseDTO> componentGradeTypes;    
    private List<ComponentIndicatorResponseDTO> componentIndicators;

}
