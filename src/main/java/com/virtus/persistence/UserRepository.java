package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User> {

    public Optional<User> findByUsername(String username);

    Boolean existsByUsernameAndIdNot(String username, Integer id);

    @Query("select u from User u where u.name LIKE %:filter%")
    Page<User> findAllByFilter(String filter, PageRequest pageRequest);

    @Query("SELECT u FROM User u WHERE u not in (SELECT m.user from Member m) ORDER BY u.name")
    Page<User> findAllNotMember(PageRequest of);

    @Query("SELECT u FROM User u WHERE u.role.id = :roleId")
    List<User> findByRoleId(Integer roleId);
}
