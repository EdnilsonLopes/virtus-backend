package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Role;
import com.virtus.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    UserDetails findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("select c from User c where c.name LIKE %:filter%")
    Page<User> findAllByFilter(String filter, PageRequest pageRequest);

}
