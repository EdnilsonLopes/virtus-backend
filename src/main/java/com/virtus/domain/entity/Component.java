package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseDefaultEntity;
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
public class Component extends BaseDefaultEntity {

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
    @Column(name = "id_componente")
    private Integer id;

    @Transient
    private int ordination;

    @Column(name = "pga")
    private boolean pga;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "component")
    private List<ElementComponent> elementComponents;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "component")
    private List<GradeTypeComponent> gradeTypeComponents;

    public Component(Integer id) {
        setId(id);
    }
}
