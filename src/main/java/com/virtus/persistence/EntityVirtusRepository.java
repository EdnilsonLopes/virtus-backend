package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.EntityVirtus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface EntityVirtusRepository extends BaseRepository<EntityVirtus> {

    @Query("select c from EntityVirtus c where c.name LIKE %:filter%" +
            " or c.acronym LIKE %:filter%" +
            " or c.code LIKE %:filter%")
    Page<EntityVirtus> findAllByFilter(String filter, PageRequest pageRequest);

}
