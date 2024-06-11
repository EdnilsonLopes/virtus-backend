package com.virtus.persistence;

import com.virtus.domain.entity.Component;
import com.virtus.domain.entity.ElementComponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementComponentRepository extends JpaRepository<ElementComponent, Integer> {
    void deleteByComponent(Component component);
}