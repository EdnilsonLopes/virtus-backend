package com.virtus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.domain.dto.response.ProductPillarHistoryDTO;
import com.virtus.service.ProductPillarHistoryService;

@RestController
@RequestMapping("/product-pillar-history")
public class ProductPillarHistoryController {

    @Autowired
    private ProductPillarHistoryService service;

    @GetMapping("/list")
    public ResponseEntity<List<ProductPillarHistoryDTO>> getHistory(
            @RequestParam Long entidadeId,
            @RequestParam Long cicloId,
            @RequestParam Long pilarId) {
        
        List<ProductPillarHistoryDTO> historyList = service.find(entidadeId, cicloId, pilarId);  
        return ResponseEntity.ok(historyList);
    }

}
