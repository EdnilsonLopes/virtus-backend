package com.virtus.controller;

import com.virtus.domain.entity.IndicatorScore;
import com.virtus.service.IndicatorScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/indicator-scores")
public class IndicatorScoreController {

    @Autowired
    private IndicatorScoreService service;

    @PostMapping("/load")
    public void loadIndicatorScores(@RequestParam String reference) {
        service.loadIndicatorScores(reference);
    }

    @GetMapping("/by-cnpb")
    public List<IndicatorScore> getByCnpb(@RequestParam String cnpb) {
        return service.findByCnpb(cnpb);
    }

    @GetMapping("/by-reference-date")
    public List<IndicatorScore> getByReferenceDate(@RequestParam String referenceDate) {
        return service.findByReferenceDate(referenceDate);
    }

    @GetMapping("/by-cnpb-reference")
    public List<IndicatorScore> getByCnpbAndReferenceDate(@RequestParam String cnpb,
                                                          @RequestParam String referenceDate) {
        return service.findByCnpbAndReferenceDate(cnpb, referenceDate);
    }

    @GetMapping("/by-indicator")
    public List<IndicatorScore> getByIndicatorCode(@RequestParam String code) {
        return service.findByIndicatorCode(code);
    }

    @GetMapping("/by-component")
    public List<IndicatorScore> getByComponentText(@RequestParam String component) {
        return service.findByComponentText(component);
    }

    @GetMapping("/by-score-range")
    public List<IndicatorScore> getByScoreRangeAndIndicator(@RequestParam BigDecimal min,
                                                            @RequestParam BigDecimal max,
                                                            @RequestParam String indicatorCode) {
        return service.findByScoreRangeAndIndicatorCode(min, max, indicatorCode);
    }

    @GetMapping
    public Page<IndicatorScore> getAllByFilter(@RequestParam(required = false) String filter,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        return service.findAllByFilter(filter, page, size);
    }
}
