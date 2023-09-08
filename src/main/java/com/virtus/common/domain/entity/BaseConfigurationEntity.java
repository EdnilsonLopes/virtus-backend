package com.virtus.common.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseConfigurationEntity extends BaseDefaultEntity {


    @Column(name = "referencia")
    private String reference;

}
