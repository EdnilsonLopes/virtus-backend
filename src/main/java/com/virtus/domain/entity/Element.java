package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseDefaultEntity;
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
public class Element extends BaseDefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator")
    @GenericGenerator(
            name = "sequence_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "virtus.hibernate_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "id_elemento")
    private Integer id;

    @Column(name = "peso")
    private int ordination;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "element")
    private List<ElementItem> items = new ArrayList<>();

    public Element(Integer id) {
        setId(id);
    }
}
