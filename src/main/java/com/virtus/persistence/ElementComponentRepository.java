package com.virtus.persistence;

import com.virtus.domain.entity.Component;
import com.virtus.domain.entity.ElementComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ElementComponentRepository extends JpaRepository<ElementComponent, Integer> {
    void deleteByComponent(Component component);

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM ElementComponent e")
    Integer findMaxId();
}