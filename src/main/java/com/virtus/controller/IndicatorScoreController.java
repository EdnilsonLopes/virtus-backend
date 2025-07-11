package com.virtus.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.IndicatorScoreRequestDTO;
import com.virtus.domain.dto.response.IndicatorScoreResponseDTO;
import com.virtus.domain.entity.IndicatorScore;
import com.virtus.service.IndicatorScoreService;

@RestController
@RequestMapping("/indicator-scores")
public class IndicatorScoreController extends
                BaseController<IndicatorScore, IndicatorScoreService, IndicatorScoreRequestDTO, IndicatorScoreResponseDTO> {

        public IndicatorScoreController(IndicatorScoreService service) {
                super(service);
        }

        @GetMapping("/last-reference")
        public ResponseEntity<Map<String, String>> getLastReferenceFromConformity() {
                String lastReference = getService().getLastReferenceFromConformity();
                return ResponseEntity.ok(Map.of("referenceDate", lastReference));
        }

}
