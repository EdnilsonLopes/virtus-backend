package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "activities_roles", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class ActivityRole {

    @Id
    @Column(name = "id_activity_role")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_activity")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;

}
