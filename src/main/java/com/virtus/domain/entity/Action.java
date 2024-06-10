package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "actions", schema = "virtus")
@Getter
@Setter
@NoArgsConstructor
public class Action extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_action")
    private Integer id;

    private String description;
    private String name;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_origin_status")
    private Status originStatus;

    @ManyToOne
    @JoinColumn(name = "id_destination_status")
    private Status destinationStatus;

    private boolean otherThan;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

    public Action(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action that = (Action) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
