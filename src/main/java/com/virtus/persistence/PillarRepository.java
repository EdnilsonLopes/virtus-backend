package com.virtus.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Pillar;

public interface PillarRepository extends BaseRepository<Pillar> {

    @Query("select c from Pillar c where c.name LIKE %:filter% OR c.description LIKE %:filter%")
    Page<Pillar> findAllByFilter(String filter, PageRequest pageRequest);

}
