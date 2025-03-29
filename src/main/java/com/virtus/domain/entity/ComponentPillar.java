package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import com.virtus.domain.enums.AverageType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "componentes_pilares", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class ComponentPillar extends BaseEntity {


    @Id
    @Column(name = "id_componente_pilar")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_componente")
    private Component component;

    @ManyToOne
    @JoinColumn(name = "id_pilar")
    private Pillar pillar;

    @Column(name = "peso_padrao")
    private Integer standardWeight;

    @Column(name = "sonda")
    private String probeFile;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_media")
    private AverageType averageType;

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentPillar that = (ComponentPillar) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
