package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.domain.dto.response.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OfficeRequestDTO extends BaseRequestDTO {

    private String name;
    private String abbreviation;
    private String description;
    private UserResponseDTO boss;
    private List<JurisdictionRequestDTO> jurisdictions = new ArrayList<>();
    private List<MemberRequestDTO> members = new ArrayList<>();

}
