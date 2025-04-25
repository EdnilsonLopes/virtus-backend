package com.virtus.domain.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.virtus.common.domain.dto.BaseRequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElementRequestDTO extends BaseRequestDTO {

    private String name;
    private String description;
    private String reference;
    private Integer ordination;
    private List<ElementItemRequestDTO> items = new ArrayList<>();

}
