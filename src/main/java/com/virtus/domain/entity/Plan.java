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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
