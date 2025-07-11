package com.virtus.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.virtus.common.domain.entity.BaseDefaultEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
