package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseDefaultEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "escritorios", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Office extends BaseDefaultEntity {

    @Id
    @Column(name = "id_escritorio")
    private Integer id;

    @Column(name = "abreviatura")
    private String abbreviation;

    @ManyToOne
    @JoinColumn(name = "id_chefe")
    private User boss;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "office")
    private List<Jurisdiction> jurisdictions = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "office")
    private List<Member> members = new ArrayList<>();

}
