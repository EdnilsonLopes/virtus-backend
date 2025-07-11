package com.virtus.domain.entity;

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
@Table(name = "produtos_componentes_historicos", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class ProductComponentHistory extends BaseEntity {

    @Id
    @Column(name = "id_produto_componente_historico")
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
    @JoinColumn(name = "id_plano")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name = "id_componente")
    private Component component;

    @ManyToOne
    @JoinColumn(name = "id_tipo_pontuacao")
    private GradeType gradeType;

    @Column(name = "peso")
    private Integer weight;

    @Column(name = "analise")
    private String analysis;

    @Column(name = "motivacao_config")
    private String configMotivation;

    @Column(name = "motivacao_peso")
    private String weightMotivation;

    @Column(name = "motivacao_nota")
    private String gradeMotivation;

    @Column(name = "motivacao_reprogramacao")
    private String reprogrammingMotivation;

    @Column(name = "justificativa")
    private String justification;

    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    private User supervisor;

    @ManyToOne
    @JoinColumn(name = "id_auditor")
    private User auditor;

    @ManyToOne
    @JoinColumn(name = "id_auditor_anterior")
    private User previousAuditor;

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "inicia_em")
    private LocalDateTime startsAt;

    @Column(name = "termina_em")
    private LocalDateTime endsAt;

    @Column(name = "inicia_em_anterior")
    private LocalDateTime previousStartsAt;

    @Column(name = "termina_em_anterior")
    private LocalDateTime previousEndsAt;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "id_versao_origem")
    private ProductComponentHistory originVersion;


}
