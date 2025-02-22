package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.dto.response.CycleEntityResponseDTO;
import com.virtus.domain.entity.CycleEntity;
import com.virtus.domain.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CycleEntityRepository extends BaseRepository<CycleEntity> {

    @Query("select ce from CycleEntity ce where ce.entity.id = ?1 and ce.cycle.id = ?2")
    Optional<CycleEntity> findByEntityIdAndCycleId(Integer entityId, Integer cycleId);


    @Modifying
    @Query(value = "UPDATE virtus.ciclos_entidades" +
            " SET id_supervisor= :supervisorId" +
            " WHERE id_entidade = :entityId AND id_ciclo = :cycleId",
            nativeQuery = true)
    int updateSupervisorByEntityIdAndCycleId(
            @Param("supervisorId") Integer supervisorId,
            @Param("entityId") Integer entityId,
            @Param("cycleId") Integer cycleId);

    @Query(value = "select ce.supervisor from CycleEntity ce where ce.entity.id = :entityId and ce.cycle.id = :cycleId")
    User findSupervisorByEntityIdAndCycleId(@Param("entityId") Integer entityId,
                                            @Param("cycleId") Integer cycleId);

    @Query("select ce from CycleEntity ce where ce.entity.id = ?1 order by ce.id desc")
    List<CycleEntity> findByEntityId(@Param("entityId") Integer entityId);

    @Query(nativeQuery = true, value = "SELECT a.* " +
            " FROM virtus.ciclos_entidades a " +
            " LEFT JOIN virtus.ciclos b ON a.id_ciclo = b.id_ciclo " +
            " WHERE a.id_entidade = :idEntidade " +
            " ORDER BY a.criado_em desc")
    List<CycleEntity> listEntidadesCiclos(@Param("idEntidade") Integer idEntidade);
}
