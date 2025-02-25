package com.virtus.domain.dto.response;

import com.virtus.domain.dto.AuditorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluatePlansTreeResponseDTO {

    private List<ProductComponentResponseDTO> products;
    private List<AuditorDTO> auditors;

}
