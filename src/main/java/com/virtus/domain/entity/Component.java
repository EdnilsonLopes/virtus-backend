package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseConfigurationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    private List<ElementComponent> elementComponents;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "component", orphanRemoval = true)
    private List<GradeTypeComponent> gradeTypeComponents;

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
