package com.virtus.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EvaluatePlansTreeNode {

    private Integer id;
    private String name;
    private String type;
    private Integer supervisorId;
    private String superVisorName;
    private Integer auditorId;
    private String auditorName;
    private Integer gradeTypeId;
    private String gradeTypeName;
    private BigDecimal weight;
    private String letter;
    private BigDecimal grade;
    private Boolean periodoPermitido;
    private Boolean periodoCiclo;
    private Boolean cicloAnalisado;
    private Boolean pilarAnalisado;
    private Boolean componenteAnalisado;
    private Boolean planoAnalisado;
    private Boolean tipoNotaAnalisado;
    private Boolean elementoAnalisado;
    private Boolean itemAnalisado;

    private Boolean cicloDescrito;
    private Boolean pilarDescrito;
    private Boolean componenteDescrito;
    private Boolean planoDescrito;
    private Boolean tipoNotaDescrito;
    private Boolean elementoDescrito;
    private Boolean itemDescrito;
    private List<EvaluatePlansTreeNode> children = new ArrayList<>();

    public void addChild(EvaluatePlansTreeNode child) {
        this.children.add(child);
    }

}
