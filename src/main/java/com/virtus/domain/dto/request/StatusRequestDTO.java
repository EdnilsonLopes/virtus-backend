package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusRequestDTO extends BaseRequestDTO {

    private String name;
    private String description;
    private String stereotype;

}
