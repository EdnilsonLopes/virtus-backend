package com.virtus.controller;

import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.dto.response.IndicatorScoreResponseDTO;
import com.virtus.domain.entity.IndicatorScore;
import com.virtus.service.IndicatorScoreService;
import com.virtus.common.domain.mapper.IndicatorScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/indicator-scores")
public class IndicatorScoreController {

    @Autowired
    private IndicatorScoreService service;

    @PostMapping("/load")
    public ResponseEntity<Void> loadIndicatorScores(@RequestParam String reference) {
        service.loadIndicatorScores(reference);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageableResponseDTO<IndicatorScoreResponseDTO>> getAllByFilter(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<IndicatorScore> resultPage = service.findAllByFilter(filter, page, size);
        List<IndicatorScoreResponseDTO> content = resultPage.getContent().stream()
                .map(IndicatorScoreMapper::toResponseDTO)
                .collect(Collectors.toList());

        PageableResponseDTO<IndicatorScoreResponseDTO> response = new PageableResponseDTO<>(
                content,
                page,
                size,
                resultPage.getTotalPages(),
                (int) resultPage.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-cnpb")
    public ResponseEntity<List<IndicatorScoreResponseDTO>> getByCnpb(@RequestParam String cnpb) {
        List<IndicatorScore> scores = service.findByCnpb(cnpb);
        return ResponseEntity.ok(IndicatorScoreMapper.toResponseDTOList(scores));
    }

    @GetMapping("/by-reference-date")
    public ResponseEntity<List<IndicatorScoreResponseDTO>> getByReferenceDate(@RequestParam String referenceDate) {
        return ResponseEntity.ok(
                IndicatorScoreMapper.toResponseDTOList(service.findByReferenceDate(referenceDate)));
    }

    @GetMapping("/by-cnpb-reference")
    public ResponseEntity<List<IndicatorScoreResponseDTO>> getByCnpbAndReferenceDate(
            @RequestParam String cnpb,
            @RequestParam String referenceDate) {
        return ResponseEntity.ok(
                IndicatorScoreMapper.toResponseDTOList(service.findByCnpbAndReferenceDate(cnpb, referenceDate)));
    }

    @GetMapping("/by-indicator")
    public ResponseEntity<List<IndicatorScoreResponseDTO>> getByIndicatorCode(@RequestParam String code) {
        return ResponseEntity.ok(
                IndicatorScoreMapper.toResponseDTOList(service.findByIndicatorCode(code)));
    }

    @GetMapping("/by-component")
    public ResponseEntity<List<IndicatorScoreResponseDTO>> getByComponentText(@RequestParam String component) {
        return ResponseEntity.ok(
                IndicatorScoreMapper.toResponseDTOList(service.findByComponentText(component)));
    }

    @GetMapping("/by-score-range")
    public ResponseEntity<List<IndicatorScoreResponseDTO>> getByScoreRangeAndIndicator(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max,
            @RequestParam String indicatorCode) {
        return ResponseEntity.ok(
                IndicatorScoreMapper
                        .toResponseDTOList(service.findByScoreRangeAndIndicatorCode(min, max, indicatorCode)));
    }

    @GetMapping("/by-id")
    public ResponseEntity<IndicatorScoreResponseDTO> getById(@RequestParam("id") Integer id) {
        IndicatorScore score = service.findById(id);
        IndicatorScoreResponseDTO dto = IndicatorScoreMapper.toResponseDTO(score);
        return ResponseEntity.ok(dto);
    }

}
