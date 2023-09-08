package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionRequestDTO extends BaseRequestDTO {

    private Integer idOriginStatus;
    private Integer idDestinationStatus;
    private String name;
    private String description;
    private boolean otherThan;

}
