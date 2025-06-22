package com.virtus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.persistence.ProductElementRepository;

@Service
public class ProductElementService {

    @Autowired
    private ProductElementRepository repository;

    public String findByCycleLevelIds(Long entidadeId, Long cicloId, Long pilarId, Long componenteId, Long planoId, Long tipoNotaId, Long elementoId) {
        return repository.findByCycleLevelIds(entidadeId, cicloId, pilarId, componenteId, planoId, tipoNotaId, elementoId);
    }

}
