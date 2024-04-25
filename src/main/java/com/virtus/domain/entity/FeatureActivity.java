package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "features_activities", schema = "virtus")
@Getter
@Setter
public class FeatureActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feature_activity")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_feature")
    private Feature feature;

    @ManyToOne
    @JoinColumn(name = "id_activity")
    private Activity activity;

}
