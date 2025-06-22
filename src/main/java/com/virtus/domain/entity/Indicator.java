package com.virtus.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.virtus.common.domain.entity.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "indicadores", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
public class Indicator extends BaseEntity {

    @Id
    @Column(name = "id_indicador")
    private Integer id;

    @Column(name = "sigla_indicador", length = 25)
    private String indicatorAcronym;

    @Column(name = "nome_indicador", length = 200)
    private String indicatorName;

    @Column(name = "descricao_indicador", length = 400)
    private String indicatorDescription;

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Indicator(Integer id) {
        this.id = id;
    }

    public Indicator() {
    }

}
