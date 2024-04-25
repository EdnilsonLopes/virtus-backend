package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos_pilares_historicos", schema = "virtus")
@Getter
@Setter
public class ProductPillarHistoric extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto_pilar_historico")
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
    @JoinColumn(name = "id_tipo_pontuacao")
    private GradeType gradeType;

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
