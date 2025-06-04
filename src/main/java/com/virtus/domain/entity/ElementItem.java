package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "itens", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class ElementItem extends BaseEntity {

    @Id
    @Column(name = "id_item")
    private Integer id;

    @Column(name = "nome")
    private String name;

    @Column(name = "descricao")
    private String description;

    @Column(name = "referencia")
    private String reference;

    @ManyToOne
    @JoinColumn(name = "id_elemento")
    private Element element;

}
