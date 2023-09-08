package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface StatusRepository extends BaseRepository<Status> {

    @Query("SELECT s FROM Status s WHERE s.name like %:filter% or s.description like %:filter% or s.stereotype like %:filter%")
    Page<Status> findAllByFilter(String filter, PageRequest pageRequest);

}
