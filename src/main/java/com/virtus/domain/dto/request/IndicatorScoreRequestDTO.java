package com.virtus.domain.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.virtus.common.domain.dto.BaseRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IndicatorScoreRequestDTO extends BaseRequestDTO {

    private Integer id;
    private String cnpb;
    private String referenceDate;
    private Integer indicatorId;
    private String indicatorSigla;
    private String componentText;
    private BigDecimal score;
    private LocalDateTime createdAt;
}
