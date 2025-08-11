package com.virtus.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.BaseController;
import com.virtus.domain.dto.request.AutomaticScoreRequestDTO;
import com.virtus.domain.dto.response.AutomaticScoreResponseDTO;
import com.virtus.domain.entity.AutomaticScore;
import com.virtus.service.AutomaticScoreService;

@RestController
@RequestMapping("/automatic-scores")
public class AutomaticScoreController extends
        BaseController<AutomaticScore, AutomaticScoreService, AutomaticScoreRequestDTO, AutomaticScoreResponseDTO> {

    public AutomaticScoreController(AutomaticScoreService service) {
        super(service);
    }

    @GetMapping("/last-reference")
    public ResponseEntity<Map<String, String>> getLastReferenceFromConformity() {
        String lastReference = getService().getLastReferenceFromIndicatorsScores();
        return ResponseEntity.ok(Map.of("referenceDate", lastReference));
    }

    @PostMapping("/calculate")
    public ResponseEntity<Void> calculateScores(@RequestBody Map<String, String> payload) {
        String referenceDate = payload.get("referenceDate");
        getService().calcularNotasAutomaticas(List.of(referenceDate));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calculateAll")
    public ResponseEntity<Void> calculateAllScores() {
        getService().calculateAll();
        return ResponseEntity.ok().build();
    }

}
