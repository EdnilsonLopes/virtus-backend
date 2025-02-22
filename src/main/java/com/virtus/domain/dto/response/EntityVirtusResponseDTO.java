package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EntityVirtusResponseDTO extends BaseResponseDTO {

    private Integer id;
    private String name;
    private String acronym;
    private String code;
    private String description;
    private String situation;
    private Boolean esi;
    private String city;
    private String uf;
    private List<CycleEntityResponseDTO> cyclesEntity = new ArrayList<>();
    private List<PlanResponseDTO> plans = new ArrayList<>();


}
