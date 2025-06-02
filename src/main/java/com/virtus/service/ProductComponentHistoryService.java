package com.virtus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.domain.dto.response.ProductComponentHistoryDTO;
import com.virtus.persistence.ProductComponentHistoryRepository;

@Service
public class ProductComponentHistoryService {

    @Autowired
    private ProductComponentHistoryRepository repository;

    public List<ProductComponentHistoryDTO> find(Long entidadeId, Long cicloId, Long pilarId, Long componenteId) {
        return repository.find(entidadeId, cicloId, pilarId, componenteId);
    }
}

