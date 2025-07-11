package com.virtus.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.virtus.common.domain.entity.BaseEntity;
import com.virtus.domain.enums.AverageType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pilares_ciclos", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class PillarCycle extends BaseEntity {

    @Id
    @Column(name = "id_pilar_ciclo")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pilar", referencedColumnName = "id_pilar")
    private Pillar pillar;

    @ManyToOne
    @JoinColumn(name = "id_ciclo", referencedColumnName = "id_ciclo")
    private Cycle cycle;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_media")
    private AverageType averageType;

    @Column(name = "peso_padrao")
    private Integer standardWeight;

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PillarCycle that = (PillarCycle) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
