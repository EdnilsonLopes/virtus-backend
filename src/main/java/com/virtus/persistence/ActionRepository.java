package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface ActionRepository extends BaseRepository<Action> {

    @Query("SELECT a FROM Action a where a.name like %:filter% " +
            "or a.description like %:filter% " +
            "or a.originStatus.name like %:filter% " +
            "or a.destinationStatus.name like %:filter%")
    Page<Action> findAllByFilter(String filter, PageRequest pageRequest);

}
