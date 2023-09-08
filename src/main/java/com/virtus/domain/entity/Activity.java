package com.virtus.domain.entity;

import javax.persistence.*;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activities", schema = "virtus")
@Getter
@Setter
public class Activity {

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
    @Column(name = "id_activity")
    private Integer id;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "expiration_time_days")
    private Integer expirationTimeDays;

    @ManyToOne
    @JoinColumn(name = "id_expiration_action")
    private Action expirationAction;

    @ManyToOne
    @JoinColumn(name = "id_action")
    private Action action;

    @ManyToOne
    @JoinColumn(name = "id_workflow")
    private Workflow workflow;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "activity")
    private List<ActivityRole> activityRoles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "activity")
    private List<FeatureActivity> featuresActivities;
}
