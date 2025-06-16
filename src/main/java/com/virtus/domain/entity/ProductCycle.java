package com.virtus.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.virtus.common.domain.entity.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "produtos_ciclos", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class ProductCycle extends BaseEntity {

    @Id
    @Column(name = "id_produto_ciclo")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_entidade")
    private EntityVirtus entity;

    @ManyToOne
    @JoinColumn(name = "id_ciclo")
    private Cycle cycle;

    @ManyToOne
    @JoinColumn(name = "id_tipo_pontuacao")
    private GradeType gradeType;

    @Column(name = "nota")
    private BigDecimal grade;

    @Column(name = "motivacao")
    private String motivation;

    @Column(name = "analise")
    private String analysis;

    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    private User supervisor;

    @ManyToOne
    @JoinColumn(name = "id_auditor")
    private User auditor;

    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;
}
