package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Cycle;
import com.virtus.domain.entity.Element;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface ElementRepository extends BaseRepository<Element> {

    @Query("select c from Element c where c.name LIKE %:filter% OR c.description LIKE %:filter%")
    Page<Element> findAllByFilter(String filter, PageRequest pageRequest);

}