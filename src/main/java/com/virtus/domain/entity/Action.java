package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "actions", schema = "virtus")
@Getter
@Setter
@NoArgsConstructor
public class Action extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @GenericGenerator(
            name = "sequence_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "virtus.hibernate_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
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
}
