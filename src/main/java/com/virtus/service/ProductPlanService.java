package com.virtus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.persistence.ProductPlanRepository;

@Service
public class ProductPlanService {

    @Autowired
    private ProductPlanRepository repository;

    public String findByCycleLevelIds(Long entidadeId, Long cicloId, Long pilarId, Long componenteId, Long planoId) {
        return repository.findByCycleLevelIds(entidadeId, cicloId, pilarId, componenteId, planoId);
    }


}
