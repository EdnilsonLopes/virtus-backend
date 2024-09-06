package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.ProductCycle;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductCycleRepository extends BaseRepository<ProductCycle> {

    @Modifying
    @Query(value = "UPDATE virtus.produtos_ciclos " +
            "SET nota = R.media " +
            "FROM (SELECT ROUND(SUM(nota * peso / 100), 2) AS media " +
            "      FROM virtus.produtos_pilares b " +
            "      WHERE b.id_entidade = :entidadeId " +
            "        AND b.id_ciclo = :cicloId " +
            "        AND b.nota <> 0 " +
            "        AND b.nota IS NOT NULL) R " +
            "WHERE id_entidade = :entidadeId " +
            "  AND id_ciclo = :cicloId",
            nativeQuery = true)
    int atualizarCicloNota(
            @Param("entidadeId") Integer entidadeId,
            @Param("cicloId") Integer cicloId);

}