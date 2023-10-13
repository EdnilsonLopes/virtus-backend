package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseDefaultEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "planos", schema = "virtus")
@Getter
@Setter
public class Plan extends BaseDefaultEntity {

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
    @Column(name = "id_plano")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_entidade")
    private EntityVirtus entity;

    @Column(name = "referencia")
    private String reference;

    @Column(name = "cnpb")
    private String cnpb;

    @Column(name = "legislacao")
    private String legislation;

    @Column(name = "situacao")
    private String situation;

    @Column(name = "recurso_garantidor")
    private Double guaranteeResource;

    @Column(name = "id_modalidade")
    private String modality;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;



}
