package com.virtus.domain.entity;


import com.virtus.common.domain.entity.BaseConfigurationEntity;
import com.virtus.common.domain.entity.BaseDefaultEntity;
import com.virtus.common.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "jurisdicoes", schema = "virtus")
@Getter
@Setter
public class Jurisdiction extends BaseEntity {

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
    @Column(name = "id_jurisdicao")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_entidade")
    private EntityVirtus entity;

    @ManyToOne
    @JoinColumn(name = "id_escritorio")
    private Office office;

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "inicia_em")
    private LocalDate startsAt;

    @Column(name = "termina_em")
    private LocalDate endsAt;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

}