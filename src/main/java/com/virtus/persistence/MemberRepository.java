package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends BaseRepository<Member> {

    @Override
    @Query("select m from Member m where m.user.name LIKE %:filter%")
    Page<Member> findAllByFilter(String filter, PageRequest pageRequest);

}
