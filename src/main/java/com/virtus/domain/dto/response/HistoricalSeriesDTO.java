package com.virtus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoricalSeriesDTO {
    private Double nota;
    private String dataReferencia;
    private String criadoEm;
}
