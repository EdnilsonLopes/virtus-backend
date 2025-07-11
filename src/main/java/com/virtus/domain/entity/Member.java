package com.virtus.domain.entity;

import java.time.LocalDate;
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
@Table(name = "membros", schema = "virtus")
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Member extends BaseEntity {

    @Id
    @Column(name = "id_membro")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_escritorio")
    private Office office;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User user;

    @Column(name = "inicia_em")
    private LocalDate startsAt;

    @Column(name = "termina_em")
    private LocalDate endsAt;

    @Column(name = "criado_em")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;
}
