package com.virtus.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.virtus.domain.entity.IndicatorComponent;

public interface IndicatorComponentRepository extends JpaRepository<IndicatorComponent, Integer> {

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM IndicatorComponent e")
    Integer findMaxId();

}