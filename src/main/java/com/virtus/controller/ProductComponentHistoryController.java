package com.virtus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.response.ProductComponentHistoryDTO;
import com.virtus.domain.model.CurrentUser;
import com.virtus.service.ProductComponentHistoryService;

@RestController
@RequestMapping("/product-component-history")
public class ProductComponentHistoryController {

    @Autowired
    private ProductComponentHistoryService service;

    @GetMapping("/list")
    public ResponseEntity<List<ProductComponentHistoryDTO>> getHistory(
            @LoggedUser CurrentUser currentUser,
            @RequestParam Long entidadeId,
            @RequestParam Long cicloId,
            @RequestParam Long pilarId,
            @RequestParam Long componenteId) {

            List<ProductComponentHistoryDTO> historyList = service.find(entidadeId, cicloId, pilarId, componenteId);
        return ResponseEntity.ok(historyList);
    }

}
