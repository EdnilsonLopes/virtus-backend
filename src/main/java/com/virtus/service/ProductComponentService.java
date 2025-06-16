package com.virtus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.persistence.ProductComponentRepository;

@Service
public class ProductComponentService {

    @Autowired
    private ProductComponentRepository repository;

    public String findByCycleLevelIds(Long entidadeId, Long cicloId, Long pilarId, Long componenteId) {
        return repository.findByCycleLevelIds(entidadeId, cicloId, pilarId, componenteId);
    }

}
