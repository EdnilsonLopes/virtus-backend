package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ProductCycleResponseDTO extends BaseResponseDTO {

    private Integer id;
    private CycleEntityResponseDTO cycle;
    private EntityVirtusResponseDTO entity;
    private String analysis;

}
