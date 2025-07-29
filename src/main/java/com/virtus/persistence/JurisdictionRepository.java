package com.virtus.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Jurisdiction;
import com.virtus.domain.entity.Office;

public interface JurisdictionRepository extends BaseRepository<Jurisdiction> {

    @Override
    @Query("select j from Jurisdiction j where j.entity.name LIKE %:filter%")
    Page<Jurisdiction> findAllByFilter(String filter, PageRequest pageRequest);
    
    @Query("select j from Jurisdiction j where j.office = :office AND (:filter IS NULL OR j.entity.name LIKE %:filter%)")
    Page<Jurisdiction> findByOffice(Office office, String filter, Pageable pageable);

    @Query("select j from Jurisdiction j JOIN j.office o where o.boss.id = ?1")
    Page<Jurisdiction> findAllByUserId(Integer userId, PageRequest pageRequest);

    @Query(nativeQuery = true, value = "SELECT DISTINCT d.codigo, b.id_entidade, d.nome, a.abreviatura " +
            "FROM virtus.escritorios a " +
            "LEFT JOIN virtus.jurisdicoes b ON a.id_escritorio = b.id_escritorio " +
            "LEFT JOIN virtus.membros c ON c.id_escritorio = b.id_escritorio " +
            "LEFT JOIN virtus.entidades d ON d.id_entidade = b.id_entidade " +
            "LEFT JOIN virtus.users u ON u.id_user = c.id_usuario " +
            "INNER JOIN virtus.ciclos_entidades e ON e.id_entidade = b.id_entidade " +
            "WHERE ((c.id_usuario = ?1 AND u.id_role = 3 AND e.id_supervisor = ?1) OR (a.id_chefe = ?1))" +
            "AND b.termina_em is null " +
            "AND e.id_supervisor IS NOT NULL " +
            "AND e.termina_em > GETDATE()")
    List<Object[]> findObjectsToDistributeActivitiesByUser(Integer userId);

    @Query("select j from Jurisdiction j where j.entity.id = :entityId")
    List<Jurisdiction> findByEntityId(Integer entityId);
}
