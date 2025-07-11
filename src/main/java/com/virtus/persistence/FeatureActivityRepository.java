package com.virtus.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.virtus.domain.entity.FeatureActivity;

public interface FeatureActivityRepository extends JpaRepository<FeatureActivity, Integer> {

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM FeatureActivity e")
    Integer findMaxId();

}