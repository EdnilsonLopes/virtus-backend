package com.virtus.persistence;

import com.virtus.domain.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM Activity e")
    Integer findMaxId();
}