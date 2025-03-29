package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

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
