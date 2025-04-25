package com.virtus.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Component;

public interface ComponentRepository extends BaseRepository<Component> {

    @Query("select c from Component c where c.name LIKE %:filter% OR c.description LIKE %:filter%")
    Page<Component> findAllByFilter(String filter, PageRequest pageRequest);

}
