package com.virtus.persistence;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.IndicatorScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IndicatorScoreRepository extends BaseRepository<IndicatorScore> {

    @Query(value = "WITH RESULT AS ( " +
            "    SELECT NU_CNPB, IND_CALC.DATA_REFER, RES_IND.ID_INDICADOR, SG_INDICADOR, " +
            "           RES_IND.ID_CALCULO, NOTA_INDICADOR, " +
            "           CASE " +
            "               WHEN SG_INDICADOR IN ('IS', 'IDP') THEN 'SOLVENCIA' " +
            "               WHEN SG_INDICADOR IN ('ILA_ILR', 'IRR', 'IASL') THEN 'ATIVOS' " +
            "               WHEN SG_INDICADOR IN ('IADE', 'IDTE', 'IPTM') THEN 'ATUARIAL' " +
            "               WHEN SG_INDICADOR = 'IAP' THEN 'RESULTADOS' " +
            "               WHEN SG_INDICADOR IN ('IDRG', 'TADE_TCRP_P') THEN 'EFICIENCIA OPERACIONAL' " +
            "           END AS COMPONENTE " +
            "    FROM CONFORMIDADE.MON.IND_RESULTADO_INDICADORES RES_IND " +
            "    JOIN CONFORMIDADE.MON.IND_INDICADORES_CALCULADOS IND_CALC ON RES_IND.ID_CALCULO = IND_CALC.ID_CALCULO "
            +
            "    JOIN CONFORMIDADE.MON.IND_DADOS_PLANOS DAD_PLN ON RES_IND.ID_DADOS_PLANO = DAD_PLN.ID_DADOS_PLANO " +
            "    JOIN CONFORMIDADE.MON.IND_DADOS_INDICADORES DAD_IND ON RES_IND.ID_INDICADOR = DAD_IND.ID_INDICADOR " +
            "    WHERE IND_CALC.DATA_REFER = :reference " +
            "    AND SG_INDICADOR IN ('IS','IDP','ILA_ILR','IRR','IASL','IADE','IDTE','IPTM','IAP','IDRG','TADE_TCRP_P') "
            +

            "    UNION " +

            "    SELECT DISTINCT NU_CNPB, IND_CALC.DATA_REFER, RES_IND.ID_INDICADOR, SG_INDICADOR, " +
            "           RES_IND.ID_CALCULO, NOTA_INDICADOR, " +
            "           CASE " +
            "               WHEN SG_INDICADOR IN ('ILA_ILR', 'IRR', 'IASL') THEN 'SUPERVISÃO PERIÓDICA - INVESTIMENTOS / ATIVOS' "
            +
            "               WHEN SG_INDICADOR IN ('IADE', 'IDTE', 'IDP') THEN 'SUPERVISÃO PERIÓDICA - EQUILÍBRIO TÉCNICO / SOLVÊNCIA' "
            +
            "               WHEN SG_INDICADOR = 'IPTM' THEN 'SUPERVISÃO PERIÓDICA - ATUARIAL / RESULTADOS' " +
            "               WHEN SG_INDICADOR IN ('IDRG', 'TADE_TCRP_P') THEN 'SUPERVISÃO PERIÓDICA - EFICIÊNCIA OPERACIONAL' "
            +
            "           END AS COMPONENTE " +
            "    FROM CONFORMIDADE.MON.IND_RESULTADO_INDICADORES RES_IND " +
            "    JOIN CONFORMIDADE.MON.IND_INDICADORES_CALCULADOS IND_CALC ON RES_IND.ID_CALCULO = IND_CALC.ID_CALCULO "
            +
            "    JOIN CONFORMIDADE.MON.IND_DADOS_PLANOS DAD_PLN ON RES_IND.ID_DADOS_PLANO = DAD_PLN.ID_DADOS_PLANO " +
            "    JOIN CONFORMIDADE.MON.IND_DADOS_INDICADORES DAD_IND ON RES_IND.ID_INDICADOR = DAD_IND.ID_INDICADOR " +
            "    WHERE IND_CALC.DATA_REFER = :reference " +
            "    AND SG_INDICADOR IN ('IDP','ILA_ILR','IRR','IASL','IADE','IDTE','IPTM','IDRG','TADE_TCRP_P') " +
            "), " +
            "MAXID AS ( " +
            "    SELECT NU_CNPB, SG_INDICADOR, MAX(ID_CALCULO) AS MAIOR_ID FROM RESULT GROUP BY NU_CNPB, SG_INDICADOR "
            +
            ") " +
            "INSERT INTO virtus.notas_indicadores (CNPB, DATA_REFERENCIA, ID_INDICADOR, SIGLA_INDICADOR, TX_COMPONENTE, NOTA, CRIADO_EM) "
            +
            "SELECT R.NU_CNPB, R.DATA_REFER, R.ID_INDICADOR, R.SG_INDICADOR, R.COMPONENTE, R.NOTA_INDICADOR, GETDATE() "
            +
            "FROM MAXID M " +
            "JOIN RESULT R ON M.NU_CNPB = R.NU_CNPB AND M.MAIOR_ID = R.ID_CALCULO " +
            "WHERE NOT EXISTS ( " +
            "    SELECT 1 FROM virtus.notas_indicadores NI " +
            "    WHERE NI.CNPB = R.NU_CNPB AND NI.DATA_REFERENCIA = R.DATA_REFER AND NI.SIGLA_INDICADOR = R.SG_INDICADOR "
            +
            ")", nativeQuery = true)
    void loadIndicatorScores(@Param("reference") String reference);

    @Query(value = "SELECT * FROM virtus.notas_indicadores WHERE cnpb = :cnpb", nativeQuery = true)
    List<IndicatorScore> findByCnpb(@Param("cnpb") String cnpb);

    @Query(value = "SELECT * FROM virtus.notas_indicadores WHERE data_referencia = :referenceDate", nativeQuery = true)
    List<IndicatorScore> findByReferenceDate(@Param("referenceDate") String referenceDate);

    @Query(value = "SELECT * FROM virtus.notas_indicadores WHERE cnpb = :cnpb AND data_referencia = :referenceDate", nativeQuery = true)
    List<IndicatorScore> findByCnpbAndReferenceDate(@Param("cnpb") String cnpb,
            @Param("referenceDate") String referenceDate);

    @Query(value = "SELECT * FROM virtus.notas_indicadores WHERE sigla_indicador = :indicatorCode", nativeQuery = true)
    List<IndicatorScore> findByIndicatorCode(@Param("indicatorCode") String indicatorCode);

    @Query(value = "SELECT * FROM virtus.notas_indicadores WHERE tx_componente = :componentText", nativeQuery = true)
    List<IndicatorScore> findByComponentText(@Param("componentText") String componentText);

    @Query(value = "SELECT * FROM virtus.notas_indicadores WHERE nota BETWEEN :minScore AND :maxScore AND sigla_indicador = :indicatorCode", nativeQuery = true)
    List<IndicatorScore> findByScoreRangeAndIndicatorCode(@Param("minScore") BigDecimal minScore,
            @Param("maxScore") BigDecimal maxScore,
            @Param("indicatorCode") String indicatorCode);

    @Query(value = "SELECT * FROM virtus.notas_indicadores " +
            "WHERE sigla_indicador LIKE %:filter% " +
            "   OR tx_componente LIKE %:filter% " +
            "   OR data_referencia LIKE %:filter%", countQuery = "SELECT count(*) FROM virtus.notas_indicadores " +
                    "WHERE sigla_indicador LIKE %:filter% " +
                    "   OR tx_componente LIKE %:filter% " +
                    "   OR data_referencia LIKE %:filter%", nativeQuery = true)
    Page<IndicatorScore> findAllByFilter(@Param("filter") String filter, Pageable pageable);

}
