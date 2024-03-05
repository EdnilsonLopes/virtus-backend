package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Jurisdiction;
import com.virtus.domain.entity.Office;
import com.virtus.domain.model.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface JurisdictionRepository extends BaseRepository<Jurisdiction> {


    @Override
    @Query("select j from Jurisdiction j where j.entity.name LIKE %:filter%")
    Page<Jurisdiction> findAllByFilter(String filter, PageRequest pageRequest);

    Page<Jurisdiction> findByOffice(Office office, Pageable pageable);

    @Query("select j from Jurisdiction j JOIN j.office o where o.boss.id = ?1")
    Page<Jurisdiction> findAllByUserId(Integer userId, PageRequest pageRequest);
}
