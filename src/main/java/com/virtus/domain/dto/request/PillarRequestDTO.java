package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PillarRequestDTO extends BaseRequestDTO {

    private String name;
    private String description;
    private String reference;
    private Integer ordination;
    private List<PillarComponentRequestDTO> components;

}
