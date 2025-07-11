package com.virtus.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.virtus.common.domain.entity.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "elementos_componentes", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class ElementComponent extends BaseEntity {

    @Id
    @Column(name = "id_elemento_componente")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_componente")
    private Component component;
    @ManyToOne
    @JoinColumn(name = "id_elemento")
    private Element element;
    @ManyToOne
    @JoinColumn(name = "id_tipo_nota")
    private GradeType gradeType;

    @Column(name = "peso_padrao")
    private Integer standardWeight;

    @Column(name = "id_versao_origem")
    private Integer originVersion;

    @Column(name = "criado_em")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementComponent that = (ElementComponent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
