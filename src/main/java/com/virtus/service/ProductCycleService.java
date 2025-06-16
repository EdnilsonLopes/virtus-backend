package com.virtus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.persistence.ProductCycleRepository;

@Service
public class ProductCycleService {

    @Autowired
    private ProductCycleRepository repository;

    public String findByCycleLevelIds(Long entidadeId, Long cicloId) {
        return repository.findByCycleLevelIds(entidadeId, cicloId);
    }

}
