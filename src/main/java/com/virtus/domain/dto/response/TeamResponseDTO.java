package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponseDTO extends BaseResponseDTO {

    private EntityVirtusResponseDTO entity;
    private OfficeResponseDTO office;
    private CycleResponseDTO cycle;

}
