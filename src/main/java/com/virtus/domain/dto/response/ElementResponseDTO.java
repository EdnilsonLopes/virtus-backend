package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ElementResponseDTO extends BaseResponseDTO {

    private String name;
    private String description;
    private String reference;
    private UserResponseDTO author;
    private Integer ordination;
    private List<ElementItemResponseDTO> items = new ArrayList<>();

}
