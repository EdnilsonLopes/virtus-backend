package com.virtus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.domain.dto.response.HistoricalSeriesDTO;
import com.virtus.domain.dto.response.ProductPlanHistoryDTO;
import com.virtus.service.ProductPlanHistoryService;

@RestController
@RequestMapping("/product-plan-history")
public class ProductPlanHistoryController {

    @Autowired
    private ProductPlanHistoryService service;

    @GetMapping("/list")
    public ResponseEntity<List<ProductPlanHistoryDTO>> getHistory(
            @RequestParam Long entidadeId,
            @RequestParam Long cicloId,
            @RequestParam Long pilarId,
            @RequestParam Long componenteId,
            @RequestParam Long planoId) {

        List<ProductPlanHistoryDTO> historyList = service.find(entidadeId, cicloId, pilarId, componenteId, planoId);
        return ResponseEntity.ok(historyList);
    }

    @GetMapping("/historical-series")
    public ResponseEntity<List<HistoricalSeriesDTO>> getHistoricalSeries(
            @RequestParam Long entidadeId,
            @RequestParam Long cicloId,
            @RequestParam Long pilarId,
            @RequestParam Long componenteId,
            @RequestParam Long planoId) {

        List<HistoricalSeriesDTO> historicalSeries = service.findHistoricalSeries(entidadeId, cicloId, pilarId, componenteId, planoId);
        return ResponseEntity.ok(historicalSeries);
    }

}
