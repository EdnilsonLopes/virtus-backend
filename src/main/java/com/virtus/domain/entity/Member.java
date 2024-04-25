package com.virtus.domain.entity;

import com.virtus.common.domain.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "membros", schema = "virtus")
@Getter
@Setter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
