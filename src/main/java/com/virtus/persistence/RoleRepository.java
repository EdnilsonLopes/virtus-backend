package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Pillar;
import com.virtus.domain.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepository extends BaseRepository<Role> {

    Optional<Role> findByName(String name);

    @Query("select c from Role c where c.name LIKE %:filter% OR c.description LIKE %:filter%")
    Page<Role> findAllByFilter(String filter, PageRequest pageRequest);

    Optional<Role> findTopByOrderByIdDesc();

}
