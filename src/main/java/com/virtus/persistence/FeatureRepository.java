package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Feature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface FeatureRepository extends BaseRepository<Feature> {

    @Query("SELECT f FROM Feature f where f.name LIKE %:filter% or f.code LIKE %:filter% or f.description LIKE %:filter%")
    Page<Feature> findAllByFilter(String filter, PageRequest pageRequest);

}
