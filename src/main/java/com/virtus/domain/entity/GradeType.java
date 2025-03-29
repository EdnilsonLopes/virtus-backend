package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseConfigurationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
