package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class JurisdictionResponseDTO extends BaseResponseDTO {

    private Integer id;
    private EntityVirtusResponseDTO entity;
    private LocalDate startsAt;
    private LocalDate endsAt;
}
