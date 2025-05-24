package com.virtus.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentGradesDTO {
    private String cicloNota;
    private String pilarNota;
    private String componenteNota;
    private String planoNota;
    private String tipoNotaNota;
}

