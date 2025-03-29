package com.virtus.persistence;

import com.virtus.domain.entity.GradeTypeComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GradeTypeComponentRepository extends JpaRepository<GradeTypeComponent, Integer> {

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM GradeTypeComponent e")
    Integer findMaxId();

}