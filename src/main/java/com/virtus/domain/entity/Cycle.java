package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseConfigurationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ciclos", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Cycle extends BaseConfigurationEntity {

    @Id
    @Column(name = "id_ciclo")
    private Integer id;
    @Transient
    private int ordination;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cycle", orphanRemoval = true)
    private List<PillarCycle> pillarCycles = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cycle")
    private List<CycleEntity> cycleEntities = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cycle")
    private List<ProductPillar> productsPillars = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cycle")
    private List<ProductCycle> productsCycles = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cycle")
    private List<ProductComponent> productsComponents = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cycle that = (Cycle) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
