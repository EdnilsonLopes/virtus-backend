package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "integrantes", schema = "virtus")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class TeamMember extends BaseEntity {

    @Id
    @Column(name = "id_integrante")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entidade")
    private EntityVirtus entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ciclo")
    private Cycle cycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private User user;

    @Column(name = "inicia_em")
    private LocalDate startsAt;

    @Column(name = "termina_em")
    private LocalDate endsAt;

    @Column(name = "motivacao")
    private String motivation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamMember that = (TeamMember) o;
        if (this.id == null || that.id == null) return super.equals(o);
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        }
        return Objects.hash(id);
    }
}
