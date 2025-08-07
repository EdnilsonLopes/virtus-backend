package com.virtus.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.virtus.common.domain.entity.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notas_indicadores", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
public class IndicatorScore extends BaseEntity {

    @Id
    @Column(name = "id_nota_indicador")
    private Integer id;

    @Column(name = "cnpb", nullable = false, length = 25)
    private String cnpb;

    @Column(name = "data_referencia", length = 6)
    private String referenceDate;

    @Column(name = "id_indicador", nullable = false)
    private Integer indicatorId;

    @Column(name = "sigla_indicador", length = 25)
    private String indicatorSigla;

    @Column(name = "nota", nullable = false)
    private BigDecimal score;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public IndicatorScore(Integer id) {
        this.id = id;
    }

    public IndicatorScore() {
    }

}
