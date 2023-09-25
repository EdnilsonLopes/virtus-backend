package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityVirtusRequestDTO extends BaseRequestDTO {

    private Integer id;
    private String name;
    private String acronym;
    private String code;
    private String description;
    private String situation;
    private Boolean esi;
    private String city;
    private String uf;

}
