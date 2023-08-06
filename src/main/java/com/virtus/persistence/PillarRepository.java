package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Element;
import com.virtus.domain.entity.Pillar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface PillarRepository extends BaseRepository<Pillar> {

    @Query("select c from Pillar c where c.name LIKE %:filter% OR c.description LIKE %:filter%")
    Page<Pillar> findAllByFilter(String filter, PageRequest pageRequest);

}
