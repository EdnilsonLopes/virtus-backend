package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import com.virtus.domain.enums.EntityInSystem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "workflows", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Workflow extends BaseEntity {

    @Id
    @Column(name = "id_workflow")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type")
    private EntityInSystem entityType;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "workflow", orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

}

