package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseConfigurationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "componentes", schema = "virtus")
@Getter
@Setter
@NoArgsConstructor
public class Component extends BaseConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_componente")
    private Integer id;

    @Transient
    private int ordination;

    @Column(name = "pga")
    private Boolean pga;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "component")
    private List<ElementComponent> elementComponents;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "component")
    private List<GradeTypeComponent> gradeTypeComponents;

    public Component(Integer id) {
        setId(id);
    }
}
