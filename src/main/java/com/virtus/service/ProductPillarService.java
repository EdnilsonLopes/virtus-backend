package com.virtus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.persistence.ProductPillarRepository;

@Service
public class ProductPillarService {

    @Autowired
    private ProductPillarRepository repository;

    public String findByCycleLevelIds(Long entidadeId, Long cicloId, Long pilarId) {
        return repository.findByCycleLevelIds(entidadeId, cicloId, pilarId);
    }

}
