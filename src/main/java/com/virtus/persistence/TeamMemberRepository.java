package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Cycle;
import com.virtus.domain.entity.TeamMember;
import com.virtus.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TeamMemberRepository extends BaseRepository<TeamMember> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM virtus.integrantes WHERE id_entidade= :entityId and id_ciclo = :cycleId")
    int deleteByEntityIdAndCycleId(@Param("entityId") Integer entityId, @Param("cycleId") Integer cycleId);

    @Query("select tm from TeamMember tm where tm.entity.id = :entityId and tm.cycle.id = :cycleId")
    List<TeamMember> findByEntityIdAndCycleId(@Param("entityId") Integer entityId, @Param("cycleId") Integer cycleId);

    Optional<TeamMember> findByUser(User user);

    @Query("SELECT b FROM CycleEntity a " +
            "INNER JOIN TeamMember b ON a.cycle.id = b.cycle.id " +
            "AND a.entity.id = b.entity.id " +
            "WHERE a.endsAt >= GETDATE() AND b.user = ?1 ")
    Optional<Page<TeamMember>> findByUserAndCycle(User user, Pageable pageable);
}
