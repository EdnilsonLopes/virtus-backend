package com.virtus.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    @OneToMany(mappedBy = "indicator", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<IndicatorComponent> indicatorComponents = new ArrayList<>();

    public Indicator(Integer id) {
        this.id = id;
    }

    public Indicator() {
    }

}
