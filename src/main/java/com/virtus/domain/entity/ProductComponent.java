package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos_componentes", schema = "virtus")
@Getter
@Setter
public class ProductComponent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto_componente")
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
    @JoinColumn(name = "id_tipo_pontuacao")
    private GradeType gradeType;

    @Column(name = "nota")
    private BigDecimal grade;

    @Column(name = "peso")
    private BigDecimal weight;

    @Column(name = "analise")
    private String analysis;

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

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "inicia_em")
    private LocalDateTime startsAt;

    @Column(name = "termina_em")
    private LocalDateTime endsAt;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;


}
