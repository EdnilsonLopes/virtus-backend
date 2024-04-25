package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import com.virtus.domain.enums.AverageType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos_ciclos", schema = "virtus")
@Getter
@Setter
public class ProductCycle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
