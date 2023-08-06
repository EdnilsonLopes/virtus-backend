package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PillarResponseDTO extends BaseResponseDTO {

    private String name;
    private String description;
    private String reference;
    private int ordination;
    private List<PillarComponentResponseDTO> components;
    private UserResponseDTO author;

}
