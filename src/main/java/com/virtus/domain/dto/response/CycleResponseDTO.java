package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CycleResponseDTO extends BaseResponseDTO {

    private Integer id;
    private int ordination;
    private String name;
    private String description;
    private String reference;
    private List<CyclePillarResponseDTO> cyclePillars;
    private List<EntityVirtusResponseDTO> entities;

}
