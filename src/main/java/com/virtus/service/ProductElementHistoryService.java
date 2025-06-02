package com.virtus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.domain.dto.request.ProductElementRequestDTO;
import com.virtus.domain.dto.response.ProductElementHistoryDTO;
import com.virtus.domain.model.CurrentUser;
import com.virtus.persistence.ProductElementHistoryRepository;

@Service
public class ProductElementHistoryService {

    @Autowired
    private ProductElementHistoryRepository repository;

    public List<ProductElementHistoryDTO> find(Long entidadeId, Long cicloId, Long pilarId, Long componenteId, Long planoId, Long elementoId) {
        return repository.find(entidadeId, cicloId, pilarId, componenteId, planoId, elementoId);
    }

    public void recordElementGradeHistory(ProductElementRequestDTO dto, CurrentUser currentUser) {
        repository.recordElementGradeHistory(dto, currentUser);
    }

    public void recordElementWeightHistory(ProductElementRequestDTO dto, CurrentUser currentUser) {
        repository.recordElementWeightHistory(dto, currentUser);
    }
}

