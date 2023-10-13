package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseDefaultEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entidades", schema = "virtus")
@Getter
@Setter
public class EntityVirtus extends BaseDefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @GenericGenerator(
            name = "sequence_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "virtus.hibernate_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "id_entidade")
    private Integer id;

    @Column(name = "sigla")
    private String acronym;

    @Column(name = "codigo")
    private String code;

    @Column(name = "situacao")
    private String situation;

    private Boolean esi;

    @Column(name = "municipio")
    private String city;

    @Column(name = "sigla_uf")
    private String uf;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "entity")
    private List<CycleEntity> cycleEntities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "entity")
    private List<Plan> plans = new ArrayList<>();
}
