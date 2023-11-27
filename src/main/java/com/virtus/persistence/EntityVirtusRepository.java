package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.EntityVirtus;
import com.virtus.domain.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface EntityVirtusRepository extends BaseRepository<EntityVirtus> {

    @Query("select c from EntityVirtus c where c.name LIKE %:filter%" +
            " or c.acronym LIKE %:filter%" +
            " or c.code LIKE %:filter%")
    Page<EntityVirtus> findAllByFilter(String filter, PageRequest pageRequest);

    @Query("select DISTINCT  new com.virtus.domain.model.Team(c, a) " +
            "from Office a " +
            "left join Jurisdiction b on a = b.office " +
            "left join EntityVirtus c on c = b.entity " +
            "inner join CycleEntity d on d.entity = b.entity " +
            "where a.boss.id = :bossId")
    Page<Team> findByEntitiesByUserBoss(int bossId, Pageable pageable);
}
