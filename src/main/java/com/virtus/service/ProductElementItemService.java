package com.virtus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.persistence.ProductComponentRepository;
import com.virtus.persistence.ProductItemRepository;

@Service
public class ProductElementItemService {

    @Autowired
    private ProductItemRepository repository;

    public String findByCycleLevelIds(Long entidadeId, Long cicloId, Long pilarId, Long componenteId, Long planoId, Long tipoNotaId, Long elementoId, Long itemId) {
        return repository.findByCycleLevelIds(entidadeId, cicloId, pilarId, componenteId, planoId, tipoNotaId, elementoId, itemId);
    }

}
