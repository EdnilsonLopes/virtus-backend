package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CycleResponseDTO extends BaseResponseDTO {

    private int ordination;
    private String name;
    private String description;
    private String reference;
    private List<CyclePillarResponseDTO> cyclePillars;
    private List<EntityVirtusResponseDTO> entities;

}
