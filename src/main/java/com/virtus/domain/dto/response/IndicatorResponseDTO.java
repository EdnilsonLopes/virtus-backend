package com.virtus.domain.dto.response;

import com.virtus.common.domain.dto.BaseResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IndicatorResponseDTO extends BaseResponseDTO {

    private Integer id;
    private String indicatorAcronym;
    private String indicatorName;
    private String indicatorDescription;
    private String authorName;
    private java.time.LocalDateTime createdAt;

}
