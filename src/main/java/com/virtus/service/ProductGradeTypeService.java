package com.virtus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.persistence.ProductGradeTypeRepository;

@Service
public class ProductGradeTypeService {

    @Autowired
    private ProductGradeTypeRepository repository;

    public String findByCycleLevelIds(Long entidadeId, Long cicloId, Long pilarId, Long componenteId, Long planoId, Long tipoNotaId) {
        return repository.findByCycleLevelIds(entidadeId, cicloId, pilarId, componenteId, planoId, tipoNotaId);
    }

}
