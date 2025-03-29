package com.virtus.persistence;

import com.virtus.domain.entity.FeatureRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeatureRoleRepository extends JpaRepository<FeatureRole, Integer> {

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM FeatureRole e")
    Integer findMaxId();
}