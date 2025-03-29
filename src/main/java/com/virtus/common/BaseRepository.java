package com.virtus.common;

import com.virtus.common.domain.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Integer> {

    @Query("SELECT t FROM #{#entityName} t")
    Page<T> findAllByFilter(String filter, PageRequest pageRequest);

    @Query("SELECT COALESCE(MAX(e.id), 0) FROM #{#entityName} e")
    Integer findMaxId();
}
