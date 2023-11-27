package com.virtus.domain.model;

import com.virtus.domain.entity.EntityVirtus;
import com.virtus.domain.entity.Office;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    private EntityVirtus entity;
    private Office office;
    
}
