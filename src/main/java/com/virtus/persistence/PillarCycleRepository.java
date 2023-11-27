package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Cycle;
import com.virtus.domain.entity.EntityVirtus;
import com.virtus.domain.entity.PillarCycle;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PillarCycleRepository extends BaseRepository<PillarCycle> {
    @Query(value = "select pc from PillarCycle pc " +
            "where not exists " +
                "(select 1 from ProductPillar pp " +
                "where pp.entity = ?1 " +
                "and pp.cycle = pc.cycle " +
                "and pp.pillar = pc.pillar) " +
            "and pc.cycle = ?2")
    Optional<PillarCycle> findByNotExistsInProductPillar(EntityVirtus entity, Cycle cycle);
}