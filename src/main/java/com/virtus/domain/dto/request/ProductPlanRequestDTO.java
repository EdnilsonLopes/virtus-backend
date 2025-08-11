package com.virtus.domain.dto.request;

import com.virtus.common.domain.dto.BaseRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPlanRequestDTO extends BaseRequestDTO {

    private Integer entidadeId;    // ID da Entidade
    private Integer cicloId;       // ID do Ciclo
    private Integer pilarId;       // ID do Pilar
    private Integer planoId;       // ID do Plano
    private Integer componenteId;  // ID do Componente
    private Integer peso;          // Peso associado ao plano
    private Integer nota;          // Nota do plano
    private String motivacao;      // Motivação da nota (justificativa para a alteração)

}
