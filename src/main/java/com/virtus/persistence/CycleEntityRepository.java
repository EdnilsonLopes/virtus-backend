package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.CycleEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CycleEntityRepository extends BaseRepository<CycleEntity> {

    @Query("select ce from CycleEntity ce where ce.entity.id = ?1 and ce.cycle.id = ?2")
    Optional<CycleEntity> findByEntityIdAndCycleId(Integer entityId, Integer cycleId);


}
