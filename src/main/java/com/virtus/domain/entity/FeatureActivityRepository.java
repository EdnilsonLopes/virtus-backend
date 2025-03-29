package com.virtus.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeatureActivityRepository extends JpaRepository<FeatureActivity, Integer> {

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM FeatureActivity e")
    Integer findMaxId();

}