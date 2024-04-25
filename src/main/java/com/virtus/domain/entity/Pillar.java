package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseConfigurationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "pillar")
    private List<ComponentPillar> componentPillars;

    public Pillar(Integer id) {
        setId(id);
    }
}
