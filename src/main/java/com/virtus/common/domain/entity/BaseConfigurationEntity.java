package com.virtus.common.domain.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseConfigurationEntity extends BaseDefaultEntity {


    @Column(name = "referencia")
    private String reference;

}
