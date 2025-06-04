package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ElementItemResponseDTO extends BaseResponseDTO {

    public ElementItemResponseDTO() {
        //TODO Auto-generated constructor stub
    }
    private String name;
    private String description;
    private String reference;

}
