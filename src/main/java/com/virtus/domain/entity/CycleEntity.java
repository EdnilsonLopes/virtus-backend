package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import com.virtus.domain.enums.AverageType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ciclos_entidades", schema = "virtus")
@Getter
@Setter
public class CycleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciclo_entidade")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_ciclo")
    private Cycle cycle;

    @ManyToOne
    @JoinColumn(name = "id_entidade")
    private EntityVirtus entity;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_media")
    private AverageType averageType;

    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    private User supervisor;

    @Column(name = "inicia_em")
    private LocalDate startsAt;

    @Column(name = "termina_em")
    private LocalDate endsAt;

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CycleEntity that = (CycleEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
