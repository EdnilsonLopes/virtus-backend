package com.virtus.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.virtus.domain.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {

    @Query(value = "select p from Plan p where p.entity.id = :entityId")
    List<Plan> findByEntityId(@Param("entityId") Integer entityId);

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM Plan e")
    Integer findMaxId();
}