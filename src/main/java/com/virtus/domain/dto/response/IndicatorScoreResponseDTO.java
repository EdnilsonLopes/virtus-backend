package com.virtus.domain.dto.response;

import java.math.BigDecimal;

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
public class IndicatorScoreResponseDTO extends BaseResponseDTO {

    private Integer id;
    private String cnpb;
    private String referenceDate;
    private Integer indicatorId;
    private String indicatorSigla;
    private BigDecimal score;
    private String authorName;
    private java.time.LocalDateTime createdAt;
}
