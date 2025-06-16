package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.ProductPillar;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductPillarRepository extends BaseRepository<ProductPillar> {

        @Modifying
        @Query(value = "UPDATE virtus.produtos_pilares " +
                        "SET nota = (SELECT " +
                        "                 ROUND(SUM(b.nota * b.peso) / SUM(b.peso), 2) AS media " +
                        "             FROM virtus.produtos_componentes b " +
                        "             WHERE " +
                        "                 produtos_pilares.id_entidade = b.id_entidade " +
                        "                 AND produtos_pilares.id_ciclo = b.id_ciclo " +
                        "                 AND produtos_pilares.id_pilar = b.id_pilar " +
                        "                 AND (b.nota IS NOT NULL AND b.nota <> 0) " +
                        "             GROUP BY b.id_entidade, " +
                        "                      b.id_ciclo, " +
                        "                      b.id_pilar) " +
                        "WHERE id_entidade = :entidadeId " +
                        "  AND id_ciclo = :cicloId " +
                        "  AND id_pilar = :pilarId", nativeQuery = true)
        int atualizarPilarNota(
                        @Param("entidadeId") Integer entidadeId,
                        @Param("cicloId") Integer cicloId,
                        @Param("pilarId") Integer pilarId);

        @Query(value = "SELECT analise FROM virtus.produtos_pilares WHERE id_entidade = :entidadeId AND id_ciclo = :cicloId AND id_pilar = :pilarId", nativeQuery = true)
        String findByCycleLevelIds(Long entidadeId, Long cicloId, Long pilarId);
}