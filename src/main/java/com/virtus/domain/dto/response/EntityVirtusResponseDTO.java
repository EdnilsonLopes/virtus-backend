package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
