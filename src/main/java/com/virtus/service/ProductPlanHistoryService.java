package com.virtus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.domain.dto.response.ProductPlanHistoryDTO;
import com.virtus.persistence.ProductPlanHistoryRepository;

@Service
public class ProductPlanHistoryService {

    @Autowired
    private ProductPlanHistoryRepository repository;

    public List<ProductPlanHistoryDTO> find(Long entidadeId, Long cicloId, Long pilarId, Long componenteId, Long planoId) {
        return repository.find(entidadeId, cicloId, pilarId, componenteId, planoId);
    }

}
