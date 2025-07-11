package com.virtus.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.IndicatorRequestDTO;
import com.virtus.domain.dto.response.IndicatorResponseDTO;
import com.virtus.domain.entity.Indicator;
import com.virtus.service.IndicatorService;

@RestController
@RequestMapping("/indicators")
public class IndicatorController
        extends BaseController<Indicator, IndicatorService, IndicatorRequestDTO, IndicatorResponseDTO> {

    public IndicatorController(IndicatorService service) {
        super(service);
    }

    @GetMapping("/sync")
    public ResponseEntity<Map<String, String>> sync() {
        getService().syncFromRemoteApi();
        return ResponseEntity.ok(Map.of("message", "Sincronização realizada com sucesso!"));
    }

}
