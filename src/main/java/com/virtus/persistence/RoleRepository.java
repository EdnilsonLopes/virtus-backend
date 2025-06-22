package com.virtus.persistence;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Role;

public interface RoleRepository extends BaseRepository<Role> {

    Optional<Role> findByName(String name);

    @Query("select c from Role c where c.name LIKE %:filter% OR c.description LIKE %:filter%")
    Page<Role> findAllByFilter(String filter, PageRequest pageRequest);

    Optional<Role> findTopByOrderByIdDesc();

}
