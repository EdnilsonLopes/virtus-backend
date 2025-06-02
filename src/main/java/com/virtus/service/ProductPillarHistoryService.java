package com.virtus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virtus.domain.dto.request.ProductPillarRequestDTO;
import com.virtus.domain.dto.response.ProductPillarHistoryDTO;
import com.virtus.domain.model.CurrentUser;
import com.virtus.persistence.ProductPillarHistoryRepository;

@Service
public class ProductPillarHistoryService {

    @Autowired
    private ProductPillarHistoryRepository repository;

    public List<ProductPillarHistoryDTO> find(Long entidadeId, Long cicloId, Long pilarId) {
        return repository.find(entidadeId, cicloId, pilarId);
    }

    public void recordPillarWeight(ProductPillarRequestDTO dto, CurrentUser currentUser) {
        String changeType = "P";
        repository.record(dto, currentUser, changeType);
    }
}

