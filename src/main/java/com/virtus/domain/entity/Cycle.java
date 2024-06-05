package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseConfigurationEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ciclos", schema = "virtus")
@Getter
@Setter
public class Cycle extends BaseConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciclo")
    private Integer id;
    @Transient
    private int ordination;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cycle")
    private List<PillarCycle> pillarCycles;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cycle")
    private List<CycleEntity> cycleEntities;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cycle")
    private List<ProductPillar> productsPillars;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cycle")
    private List<ProductCycle> productsCycles;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cycle")
    private List<ProductComponent> productsComponents;

}
