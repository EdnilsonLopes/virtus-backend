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
@Table(name = "produtos_elementos_historicos", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class ProductElementHistory extends BaseEntity {

    @Id
    @Column(name = "id_produto_elemento_historico")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_entidade")
    private EntityVirtus entity;

    @ManyToOne
    @JoinColumn(name = "id_ciclo")
    private Cycle cycle;

    @ManyToOne
    @JoinColumn(name = "id_pilar")
    private Pillar pillar;

    @ManyToOne
    @JoinColumn(name = "id_componente")
    private Component component;

    @ManyToOne
    @JoinColumn(name = "id_plano")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "id_tipo_nota")
    private GradeType gradeType;

    @ManyToOne
    @JoinColumn(name = "id_tipo_pontuacao")
    private GradeType punctuationType;

    @Column(name = "tipo_alteracao")
    private Integer changeType;

    @ManyToOne
    @JoinColumn(name = "id_elemento")
    private Element element;

    @Column(name = "peso")
    private BigDecimal weight;

    @Column(name = "nota")
    private BigDecimal grade;

    @Column(name = "analise")
    private String analysis;

    @Column(name = "motivacao_peso")
    private String weightMotivation;

    @Column(name = "motivacao_nota")
    private String gradeMotivation;

    @Column(name = "justificativa")
    private String justification;

    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    private User supervisor;

    @ManyToOne
    @JoinColumn(name = "id_auditor")
    private User auditor;

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;
}
