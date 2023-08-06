package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Cycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface CycleRepository extends BaseRepository<Cycle> {

    @Query("select c from Cycle c where c.name LIKE %:filter% OR c.description LIKE %:filter%")
    Page<Cycle> findAllByFilter(String filter, PageRequest pageRequest);

}
