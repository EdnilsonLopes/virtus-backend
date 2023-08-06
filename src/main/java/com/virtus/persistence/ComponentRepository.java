package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Component;
import com.virtus.domain.entity.GradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface ComponentRepository extends BaseRepository<Component> {

    @Query("select c from Component c where c.name LIKE %:filter% OR c.description LIKE %:filter%")
    Page<Component> findAllByFilter(String filter, PageRequest pageRequest);

}
