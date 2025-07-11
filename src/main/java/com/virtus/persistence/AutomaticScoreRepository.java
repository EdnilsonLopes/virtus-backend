package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.AutomaticScore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AutomaticScoreRepository extends BaseRepository<AutomaticScore> {

    @Query(value = "SELECT * FROM virtus.notas_automaticas WHERE cnpb = :cnpb", nativeQuery = true)
    List<AutomaticScore> findByCnpb(@Param("cnpb") String cnpb);

    @Query(value = "SELECT * FROM virtus.notas_automaticas WHERE data_referencia = :dataReferencia", nativeQuery = true)
    List<AutomaticScore> findByDataReferencia(@Param("dataReferencia") String dataReferencia);

    @Query(value = "SELECT * FROM virtus.notas_automaticas WHERE cnpb = :cnpb AND data_referencia = :dataReferencia", nativeQuery = true)
    List<AutomaticScore> findByCnpbAndDataReferencia(@Param("cnpb") String cnpb,
            @Param("dataReferencia") String dataReferencia);

    @Query(value = "SELECT * FROM virtus.notas_automaticas WHERE id_componente = :idComponente", nativeQuery = true)
    List<AutomaticScore> findByIdComponente(@Param("idComponente") Integer idComponente);

    @Query(value = "SELECT * FROM virtus.notas_automaticas WHERE nota BETWEEN :minScore AND :maxScore AND id_componente = :idComponente", nativeQuery = true)
    List<AutomaticScore> findByNotaRangeAndComponente(@Param("minScore") BigDecimal minScore,
            @Param("maxScore") BigDecimal maxScore,
            @Param("idComponente") Integer idComponente);

    @Query(value = "SELECT na.* FROM virtus.notas_automaticas na " +
            "JOIN virtus.componentes c ON na.id_componente = c.id_componente " +
            "WHERE na.id_nota_automatica LIKE %:filter% OR c.nome LIKE %:filter% " +
            "   OR na.cnpb LIKE %:filter% " +
            "   OR na.data_referencia LIKE %:filter% " +
            "   OR CAST(na.nota AS VARCHAR) LIKE %:filter%", countQuery = "SELECT COUNT(*) FROM virtus.notas_automaticas na "
                    +
                    "JOIN virtus.componentes c ON na.id_componente = c.id_componente " +
                    "WHERE na.id_nota_automatica LIKE %:filter% OR c.nome LIKE %:filter% " +
                    "   OR na.cnpb LIKE %:filter% " +
                    "   OR na.data_referencia LIKE %:filter% " +
                    "   OR CAST(na.nota AS VARCHAR) LIKE %:filter%", nativeQuery = true)
    Page<AutomaticScore> findAllByFilter(String filter, PageRequest pageRequest);

}
