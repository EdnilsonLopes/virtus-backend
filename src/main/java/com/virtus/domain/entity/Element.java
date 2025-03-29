package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseConfigurationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
