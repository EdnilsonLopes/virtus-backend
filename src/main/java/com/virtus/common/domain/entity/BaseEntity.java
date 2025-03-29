package com.virtus.common.domain.entity;

import com.virtus.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Transient
    private LocalDateTime createdAt = LocalDateTime.now();

    @Transient
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_author", updatable = false)
    private User author;

    public void setId(Integer id){

    }

    public Integer getId(){
        return null;
    }

}
