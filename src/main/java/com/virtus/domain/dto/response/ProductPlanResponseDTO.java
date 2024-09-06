package com.virtus.domain.dto.response;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPlanResponseDTO {

    private Integer id;
    private Integer entityId;
    private Integer planId;

}
