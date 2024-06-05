package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import com.virtus.domain.enums.AverageType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pilares_ciclos", schema = "virtus")
@Getter
@Setter
public class PillarCycle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pilar_ciclo")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_pilar")
    private Pillar pillar;

    @ManyToOne
    @JoinColumn(name = "id_ciclo", referencedColumnName = "id_ciclo")
    private Cycle cycle;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_media")
    private AverageType averageType;

    @Column(name = "peso_padrao")
    private Integer standardWeight;

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

}
