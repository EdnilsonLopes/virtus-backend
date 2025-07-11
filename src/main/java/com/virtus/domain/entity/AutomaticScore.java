package com.virtus.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.virtus.common.domain.entity.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notas_automaticas", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
public class AutomaticScore extends BaseEntity {

    @Id
    @Column(name = "id_nota_automatica")
    private Integer id;

    @Column(name = "cnpb", nullable = false, length = 25)
    private String cnpb;

    @Column(name = "data_referencia", length = 6)
    private String referenceDate;

    @Column(name = "id_componente", nullable = false)
    private Integer componentId;

    @Column(name = "nota", nullable = false)
    private BigDecimal score;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public AutomaticScore(Integer id) {
        this.id = id;
    }

    public AutomaticScore() {
    }
}