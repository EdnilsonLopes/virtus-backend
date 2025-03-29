package com.virtus.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRoleRepository extends JpaRepository<ActivityRole, Integer> {

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM ActivityRole e")
    Integer findMaxId();

}