package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.common.domain.dto.BaseResponseDTO;
import com.virtus.domain.dto.response.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequestDTO extends BaseRequestDTO {

    private SupervisorResponseDTO supervisor;
    private EntityVirtusResponseDTO entity;
    private OfficeResponseDTO office;
    private CycleResponseDTO cycle;

}
