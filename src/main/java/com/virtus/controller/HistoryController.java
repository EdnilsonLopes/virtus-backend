package com.virtus.controller;

import com.virtus.domain.dto.response.HistoryComponentDTO;
import com.virtus.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("/list")
    public ResponseEntity<List<HistoryComponentDTO>> getHistory(
            @RequestParam Long entidadeId,
            @RequestParam Long cicloId,
            @RequestParam Long pilarId,
            @RequestParam Long componenteId) {

        List<HistoryComponentDTO> historyList = historyService.getHistoryByFilter(entidadeId, cicloId, pilarId, componenteId);
        return ResponseEntity.ok(historyList);
    }

}
