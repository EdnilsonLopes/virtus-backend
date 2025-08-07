package com.virtus.controller;

import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.BaseController;
import com.virtus.common.annotation.LoggedUser;
import com.virtus.domain.dto.request.IndicatorScoreRequestDTO;
import com.virtus.domain.dto.response.IndicatorScoreResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.entity.IndicatorScore;
import com.virtus.domain.model.CurrentUser;
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

        @PostMapping("/syncScores")
        public ResponseEntity<Void> syncScores(@RequestBody Map<String, String> payload) {
                String referenceDate = payload.get("referenceDate");
                getService().syncScores(referenceDate);
                return ResponseEntity.ok().build();
        }

        @GetMapping
        public ResponseEntity<PageableResponseDTO<IndicatorScoreResponseDTO>> getAll(
                        @LoggedUser CurrentUser currentUser,
                        @RequestParam(required = false, defaultValue = "") String filter,
                        @RequestParam(required = false, defaultValue = "0") int page,
                        @RequestParam(required = false, defaultValue = "10") int size) {
                return ResponseEntity.ok(getService().findAllByFilter(currentUser, filter, page, size));
        }

}
