package com.virtus.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.virtus.domain.entity.ActivityRole;

public interface ActivityRoleRepository extends JpaRepository<ActivityRole, Integer> {

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM ActivityRole e")
    Integer findMaxId();

}