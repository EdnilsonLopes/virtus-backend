package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.CycleEntity;
import com.virtus.domain.entity.ProductComponent;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductComponentRepository extends BaseRepository<ProductComponent> {

    @Query("select pc from ProductComponent pc where pc.entity.id = ?1 and pc.cycle.id = ?2")
    Optional<ProductComponent> findByEntityIdAndCycleId(Integer entityId, Integer cycleId);

}