package com.virtus.service;

import com.virtus.domain.dto.response.HistoryComponentDTO;
import com.virtus.persistence.ProductHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private ProductHistoryService historyRepository;

    public List<HistoryComponentDTO> getHistoryByFilter(Long entidadeId, Long cicloId, Long pilarId, Long componenteId) {
        return historyRepository.findComponentHistoryByEntityAndCycleAndPillarAndComponent(entidadeId, cicloId, pilarId, componenteId);
    }
}

