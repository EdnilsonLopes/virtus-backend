package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import com.virtus.domain.dto.response.EntityVirtusResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JurisdictionRequestDTO extends BaseRequestDTO {

    private Integer id;
    private EntityVirtusResponseDTO entity;
    private LocalDate startsAt;
    private LocalDate endsAt;

}
