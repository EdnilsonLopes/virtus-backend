package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseDefaultEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "entidades", schema = "virtus")
@Getter
@Setter
public class EntityVirtus extends BaseDefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "entity")
    private List<CycleEntity> cycleEntities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "entity")
    private List<Plan> plans = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityVirtus that = (EntityVirtus) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
