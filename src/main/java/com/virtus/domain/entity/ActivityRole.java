package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "activities_roles", schema = "virtus")
@Getter
@Setter
public class ActivityRole {

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
    @Column(name = "id_activity_role")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_activity")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;

}
