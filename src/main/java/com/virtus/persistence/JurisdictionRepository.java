package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Jurisdiction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface JurisdictionRepository extends BaseRepository<Jurisdiction> {


    @Override
    @Query("select j from Jurisdiction j where j.entity.name LIKE %:filter%")
    Page<Jurisdiction> findAllByFilter(String filter, PageRequest pageRequest);
}
