package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.Office;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfficeRepository extends BaseRepository<Office> {

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

}
