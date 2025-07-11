package com.virtus.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
