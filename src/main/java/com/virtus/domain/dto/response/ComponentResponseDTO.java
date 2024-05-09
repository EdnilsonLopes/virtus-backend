package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComponentResponseDTO extends BaseResponseDTO {

    private String name;
    private int ordination;
    private String description;
    private String reference;
    private Boolean pga;
    private List<ComponentElementResponseDTO> componentElements;
    private List<ComponentGradeTypeResponseDTO> componentGradeTypes;

}
