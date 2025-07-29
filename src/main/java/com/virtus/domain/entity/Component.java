package com.virtus.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.virtus.common.domain.entity.BaseConfigurationEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "componentes", schema = "virtus")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Component extends BaseConfigurationEntity {

    @Id
    @Column(name = "id_componente")
    private Integer id;

    @Transient
    private int ordination;

    @Column(name = "pga")
    private Boolean pga;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "component", orphanRemoval = true)
    private List<ElementComponent> elementComponents = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "component", orphanRemoval = true)
    private List<GradeTypeComponent> gradeTypeComponents = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "component", orphanRemoval = true)
    private List<IndicatorComponent> indicatorComponents = new ArrayList<>();

    public Component(Integer id) {
        setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component that = (Component) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
