package com.virtus.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentWeightsDTO {

    private String cicloPeso;
    private String pilarPeso;
    private String componentePeso;
    private String planoPeso;
    private String tipoNotaPeso;
    private String elementoPeso;
}
