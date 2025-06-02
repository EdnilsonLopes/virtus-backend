package com.virtus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.domain.dto.response.ProductElementHistoryDTO;
import com.virtus.service.ProductElementHistoryService;

@RestController
@RequestMapping("/product-element-history")
public class ProductElementHistoryController {

    @Autowired
    private ProductElementHistoryService service;

    @GetMapping("/list")
    public ResponseEntity<List<ProductElementHistoryDTO>> getHistory(
            @RequestParam Long entidadeId,
            @RequestParam Long cicloId,
            @RequestParam Long pilarId,
            @RequestParam Long componenteId,
            @RequestParam Long planoId,
            @RequestParam Long elementoId) {

        List<ProductElementHistoryDTO> historyList = service.find(entidadeId, cicloId, pilarId, componenteId, planoId, elementoId); 
        return ResponseEntity.ok(historyList);
    }

}
