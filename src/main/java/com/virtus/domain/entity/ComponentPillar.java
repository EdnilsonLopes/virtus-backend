package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import com.virtus.domain.enums.AverageType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "componentes_pilares", schema = "virtus")
@Getter
@Setter
public class ComponentPillar extends BaseEntity {


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
    @Column(name = "id_componente_pilar")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_componente")
    private Component component;

    @ManyToOne
    @JoinColumn(name = "id_pilar")
    private Pillar pillar;

    @Column(name = "peso_padrao")
    private Integer standardWeight;

    @Column(name = "sonda")
    private String probeFile;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_media")
    private AverageType averageType;

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

}
