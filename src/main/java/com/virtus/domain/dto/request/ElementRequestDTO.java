package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ElementRequestDTO extends BaseRequestDTO {

    private String name;
    private String description;
    private String reference;
    private Integer ordination;
    private List<ElementItemRequestDTO> items = new ArrayList<>();

}
