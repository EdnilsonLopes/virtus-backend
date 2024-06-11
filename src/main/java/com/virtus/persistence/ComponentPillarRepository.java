package com.virtus.persistence;

import com.virtus.domain.entity.ComponentPillar;
import com.virtus.domain.entity.Pillar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComponentPillarRepository extends JpaRepository<ComponentPillar, Integer> {
    void deleteByPillar(Pillar pillar);

    @Query("select cp from ComponentPillar cp where cp.pillar.id = :id")
    List<ComponentPillar> findByPillarId(Integer id);
}