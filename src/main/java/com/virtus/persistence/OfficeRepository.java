package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Member;
import com.virtus.domain.entity.Office;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfficeRepository extends BaseRepository<Office> {

    @Override
    @Query("select o from Office o where o.name LIKE %:filter% OR o.abbreviation LIKE %:filter% OR o.description LIKE %:filter%")
    Page<Office> findAllByFilter(String filter, PageRequest pageRequest);

    @Query(nativeQuery = true, value =
            "SELECT    " +
                    "    b.id_usuario,    " +
                    "    coalesce(c.name,    " +
                    "    '') AS nome_usuario,    " +
                    "    coalesce(d.name,    " +
                    "    '') as role_name    " +
                    "FROM    " +
                    "    virtus.escritorios a    " +
                    "LEFT JOIN virtus.membros b ON    " +
                    "    a.id_escritorio = b.id_escritorio    " +
                    "LEFT JOIN virtus.users c ON    " +
                    "    b.id_usuario = c.id_user    " +
                    "LEFT JOIN virtus.roles d ON    " +
                    "    c.id_role = d.id_role     " +
                    "WHERE    " +
                    "    a.id_chefe = ?1    " +
                    "    AND c.id_role in (2, 3)     " +
                    "ORDER BY    " +
                    "    nome_usuario")
    List<Object[]> findAllSupervisorsByBossId(Integer bossId);

    @Query(nativeQuery = true, value = "SELECT DISTINCT d.codigo, b.id_entidade, d.nome, a.abreviatura " +
            "FROM virtus.escritorios a " +
            "LEFT JOIN virtus.jurisdicoes b ON a.id_escritorio = b.id_escritorio " +
            "LEFT JOIN virtus.membros c ON c.id_escritorio = b.id_escritorio " +
            "LEFT JOIN virtus.entidades d ON d.id_entidade = b.id_entidade " +
            "LEFT JOIN virtus.users u ON u.id_user = c.id_usuario " +
            "INNER JOIN virtus.ciclos_entidades e ON e.id_entidade = b.id_entidade " +
            "INNER JOIN virtus.produtos_planos f ON (f.id_entidade = e.id_entidade AND f.id_ciclo = e.id_ciclo) " +
            "WHERE ( " +
            "(:filter IS NULL OR LOWER(d.nome) LIKE CONCAT('%', LOWER(:filter), '%'))" +
            ") AND ( " +
            "(c.id_usuario = :idUsuario AND u.id_role IN (3, 4)) " +
            "OR (a.id_chefe = :idUsuario))")
    List<Object[]> listAvaliarPlanos(@Param("idUsuario") Integer idUsuario, @Param("filter") String filter);


}
