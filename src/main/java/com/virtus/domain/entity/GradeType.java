package com.virtus.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.virtus.common.domain.entity.BaseConfigurationEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipos_notas", schema = "virtus")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class GradeType extends BaseConfigurationEntity {

    @Id
    @Column(name = "id_tipo_nota")
    private Integer id;

    @Column(name = "letra")
    private String letter;

    @Column(name = "cor_letra")
    private String letterColor;

    @Transient
    private int ordination;

    public GradeType (Integer id){
        setId(id);
    }


}
