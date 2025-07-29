package com.virtus.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.virtus.domain.entity.Member;
import com.virtus.domain.entity.Office;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    @Query("SELECT COALESCE(MAX(m.id), 0) FROM Member m")
    Integer findMaxId();

    @Query(nativeQuery = true,
            value = "WITH subordinacoes AS (    " +
                    "SELECT    " +
                    "    b.id_usuario,    " +
                    "    a.id_supervisor    " +
                    "FROM    " +
                    "    virtus.ciclos_entidades a    " +
                    "INNER JOIN virtus.integrantes b  ON    " +
                    "    (a.id_ciclo = b.id_ciclo    " +
                    "        AND a.id_entidade = b.id_entidade)     " +
                    "WHERE    " +
                    "    a.termina_em >= GETDATE())     " +
                    "SELECT    " +
                    "    DISTINCT b.id_usuario, coalesce(c.name,    " +
                    "    '') AS nome_auditor, coalesce(d.name,    " +
                    "    '') AS role_name,coalesce(s.id_supervisor,    " +
                    "    0) AS subordinacao    " +
                    "FROM    " +
                    "    virtus.escritorios a     " +
                    "LEFT JOIN virtus.membros b ON    " +
                    "    a.id_escritorio = b.id_escritorio     " +
                    "LEFT JOIN virtus.users c ON    " +
                    "    b.id_usuario = c.id_user     " +
                    "LEFT JOIN virtus.ROLES d ON    " +
                    "    c.id_role = d.id_role     " +
                    "LEFT JOIN subordinacoes s ON    " +
                    "    B.id_usuario = s.id_usuario     " +
                    "WHERE    " +
                    "    a.id_chefe = ?1     " +
                    "    AND c.id_role in (2, 3, 4)     " +
                    "ORDER BY    " +
                    "    nome_auditor")
    List<Object[]> findMembersByBoss(Integer bossId);

    @Query("select m from Member m where m.office = :office AND (:filter IS NULL OR m.user.name LIKE %:filter%)")
    Page<Member> findAllByOffice(Office office, String filter, Pageable pageable);
}
