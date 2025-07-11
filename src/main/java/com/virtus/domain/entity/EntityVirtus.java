package com.virtus.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.virtus.common.domain.entity.BaseDefaultEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "entidades", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class EntityVirtus extends BaseDefaultEntity {

    @Id
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "entity", orphanRemoval = true)
    private List<CycleEntity> cycleEntities = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "entity", orphanRemoval = true)
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
