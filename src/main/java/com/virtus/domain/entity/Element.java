package com.virtus.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.virtus.common.domain.entity.BaseConfigurationEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "elementos", schema = "virtus")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Element extends BaseConfigurationEntity {

    @Id
    @Column(name = "id_elemento")
    private Integer id;

    @Column(name = "peso")
    private int ordination;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "element", orphanRemoval = true)
    private List<ElementItem> items = new ArrayList<>();

    public Element(Integer id) {
        setId(id);
    }
}
