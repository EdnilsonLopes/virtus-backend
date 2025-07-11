package com.virtus.domain.entity;

import java.time.LocalDateTime;

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
@Table(name = "status", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Status extends BaseEntity {

    @Id
    @Column(name = "id_status")
    private Integer id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    private String stereotype;

    private LocalDateTime createdAt = LocalDateTime.now();


}
