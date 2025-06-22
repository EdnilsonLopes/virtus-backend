package com.virtus.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.virtus.common.domain.entity.BaseDefaultEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "planos", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Plan extends BaseDefaultEntity {

    @Id
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
