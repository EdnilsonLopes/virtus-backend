package com.virtus.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "activities", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Activity {

    @Id
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activity", orphanRemoval = true)
    private List<ActivityRole> activityRoles = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "activity", orphanRemoval = true)
    private List<FeatureActivity> featuresActivities = new ArrayList<>();
}
