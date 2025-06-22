package com.virtus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtus.common.domain.mapper.IndicatorMapper;
import com.virtus.domain.dto.response.IndicatorResponseDTO;
import com.virtus.domain.dto.response.PageableResponseDTO;
import com.virtus.domain.entity.Indicator;
import com.virtus.service.IndicatorService;

@RestController
@RequestMapping("/indicators")
public class IndicatorController {

    @Autowired
    private IndicatorService service;

    @GetMapping
    public ResponseEntity<PageableResponseDTO<IndicatorResponseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String filter) {
        filter = filter != null ? filter.trim() : null;
        page = page < 0 ? page = 0 : page;
        size = size < 0 ? size = 0 : size;
        Page<Indicator> indicators = service.findByFilter(filter, page, size);
        List<IndicatorResponseDTO> content = IndicatorMapper.toDTOList(indicators.getContent());

        PageableResponseDTO<IndicatorResponseDTO> response = new PageableResponseDTO<>(
                content,
                page,
                size,
                indicators.getTotalPages(),
                (int) indicators.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-id")
    public ResponseEntity<IndicatorResponseDTO> getByIdQueryParam(@RequestParam("id") Integer id) {
        Indicator indicator = service.findById(id);
        IndicatorResponseDTO dto = new IndicatorResponseDTO();
        dto.setId(indicator.getId());
        dto.setIndicatorAcronym(indicator.getIndicatorAcronym());
        dto.setIndicatorName(indicator.getIndicatorName());
        dto.setIndicatorDescription(indicator.getIndicatorDescription());
        dto.setAuthorName(indicator.getAuthor() != null ? indicator.getAuthor().getName() : null);
        dto.setCreatedAt(indicator.getCreatedAt());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/by-description")
    public ResponseEntity<List<Indicator>> getByDescription(@RequestParam String description) {
        List<Indicator> indicators = service.findByDescriptionContaining(description);
        return ResponseEntity.ok(indicators);
    }

    @GetMapping("/by-acronyms")
    public List<Indicator> getByAcronyms(@RequestParam List<String> acronyms) {
        return service.findByAcronyms(acronyms);
    }

    @GetMapping("/acronyms")
    public List<String> getAllAcronyms() {
        return service.findAllAcronyms();
    }

    @PostMapping
    public ResponseEntity<IndicatorResponseDTO> create(@RequestBody Indicator indicator) {
        Indicator savedIndicator = service.save(indicator);
        return ResponseEntity.ok(IndicatorMapper.toResponseDTO(savedIndicator));
    }

    @PutMapping
    public ResponseEntity<IndicatorResponseDTO> update(@RequestBody Indicator indicator) {
        if (indicator.getId() == null) {
            throw new IllegalArgumentException("ID é obrigatório para atualização.");
        }
        Indicator updated = service.save(indicator);
        return ResponseEntity.ok(IndicatorMapper.toResponseDTO(updated));
    }

    @DeleteMapping
    public void delete(@RequestParam("id") Integer id) {
        service.deleteById(id);
    }

}
