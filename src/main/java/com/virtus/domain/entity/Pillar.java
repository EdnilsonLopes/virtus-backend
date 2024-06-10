package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseConfigurationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pilares", schema = "virtus")
@Getter
@Setter
@NoArgsConstructor
public class Pillar extends BaseConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pilar")
    private Integer id;

    @Transient
    private int ordination;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pillar")
    private List<ComponentPillar> componentPillars;

    public Pillar(Integer id) {
        setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pillar pillar = (Pillar) o;
        return Objects.equals(id, pillar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
