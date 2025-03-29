package com.virtus.domain.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "features_roles", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class FeatureRole {

    @Id
    @Column(name = "id_feature_role")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_feature")
    private Feature feature;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;

}
