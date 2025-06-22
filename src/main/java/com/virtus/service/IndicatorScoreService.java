package com.virtus.service;

import com.virtus.domain.entity.IndicatorScore;
import com.virtus.persistence.IndicatorScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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
        return repository.findAllByFilter(filter, PageRequest.of(page, size));
    }
}
