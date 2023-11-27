package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Cycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface CycleRepository extends BaseRepository<Cycle> {

    @Query("select c from Cycle c where c.name LIKE %:filter% OR c.description LIKE %:filter%")
    Page<Cycle> findAllByFilter(String filter, PageRequest pageRequest);

    @Query("SELECT c " +
            "FROM CycleEntity ce " +
            "LEFT JOIN ce.cycle c " +
            "WHERE ce.entity.id = ?1 " +
            "AND ce.endsAt >= ?2 " +
            "ORDER BY ce.endsAt ASC")
    Page<Cycle> findValidCyclesByEntityId(Integer entityId, LocalDate currentDate, PageRequest pageRequest);
}
