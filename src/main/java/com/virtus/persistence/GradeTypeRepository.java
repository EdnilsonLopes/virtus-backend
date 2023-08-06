package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.GradeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface GradeTypeRepository extends BaseRepository<GradeType> {

    @Query("select c from GradeType c where c.name LIKE %:filter% OR c.description LIKE %:filter%")
    Page<GradeType> findAllByFilter(String filter, PageRequest pageRequest);
}
