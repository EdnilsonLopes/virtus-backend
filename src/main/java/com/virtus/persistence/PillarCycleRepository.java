package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Cycle;
import com.virtus.domain.entity.EntityVirtus;
import com.virtus.domain.entity.PillarCycle;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying
    @Transactional
    @Query("delete from PillarCycle pc where pc.cycle.id = :cycleId")
    int deleteByCycleId(@Param("cycleId") Integer cycleId);
}