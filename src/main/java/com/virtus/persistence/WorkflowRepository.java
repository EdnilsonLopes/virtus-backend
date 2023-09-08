package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Workflow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface WorkflowRepository extends BaseRepository<Workflow> {

    @Query("SELECT w FROM Workflow w " +
            "where w.name like %:filter% " +
            "or w.description like %:filter% " +
            "or w.entityType like %:filter%")
    Page<Workflow> findAllByFilter(String filter, PageRequest pageRequest);

}
