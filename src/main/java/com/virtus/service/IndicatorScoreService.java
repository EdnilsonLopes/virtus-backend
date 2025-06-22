package com.virtus.service;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.virtus.domain.entity.IndicatorScore;
import com.virtus.persistence.IndicatorScoreRepository;

@Service
public class IndicatorScoreService {

    @Autowired
    private IndicatorScoreRepository repository;

    public void loadIndicatorScores(String reference) {
        repository.loadIndicatorScores(reference);
    }

    public List<IndicatorScore> findByCnpb(String cnpb) {
        return repository.findByCnpb(cnpb);
    }

    public List<IndicatorScore> findByReferenceDate(String referenceDate) {
        return repository.findByReferenceDate(referenceDate);
    }

    public List<IndicatorScore> findByCnpbAndReferenceDate(String cnpb, String referenceDate) {
        return repository.findByCnpbAndReferenceDate(cnpb, referenceDate);
    }

    public List<IndicatorScore> findByIndicatorCode(String indicatorCode) {
        return repository.findByIndicatorCode(indicatorCode);
    }

    public List<IndicatorScore> findByComponentText(String componentText) {
        return repository.findByComponentText(componentText);
    }

    public List<IndicatorScore> findByScoreRangeAndIndicatorCode(BigDecimal min, BigDecimal max, String indicatorCode) {
        return repository.findByScoreRangeAndIndicatorCode(min, max, indicatorCode);
    }

public Page<IndicatorScore> findAllByFilter(String filter, int page, int size) {
    
    if (filter == null || filter.trim().isEmpty()) {
        // Se não houver filtro, retorna todos ordenados por data de referência
        Pageable pageable = PageRequest.of(page, size, Sort.by("referenceDate").descending());
        return repository.findAll(pageable);
    }
    Pageable pageable = PageRequest.of(page, size, Sort.by("data_referencia").descending());
    // Se houver filtro, utiliza uma consulta nativa que busca por sigla do indicador ou texto do componente
    return repository.findAllByFilter(filter.trim(), pageable);
}


    public IndicatorScore findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota de indicador não encontrada com ID: " + id));
    }
}
