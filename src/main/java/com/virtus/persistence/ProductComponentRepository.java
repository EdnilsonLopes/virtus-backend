package com.virtus.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.virtus.common.BaseRepository;
import com.virtus.domain.entity.ProductComponent;

public interface ProductComponentRepository extends BaseRepository<ProductComponent> {

        @Query("select pc from ProductComponent pc where pc.entity.id = ?1 and pc.cycle.id = ?2 order by pc.component.id asc")
        Optional<List<ProductComponent>> findByEntityIdAndCycleId(Integer entityId, Integer cycleId);

        @Modifying
        @Query(value = "UPDATE virtus.produtos_componentes" +
                        " SET id_supervisor= :supervisorId" +
                        " WHERE id_entidade = :entityId AND id_ciclo = :cycleId", nativeQuery = true)
        int updateSupervisorByEntityIdAndCycleId(
                        @Param("supervisorId") Integer supervisorId,
                        @Param("entityId") Integer entityId,
                        @Param("cycleId") Integer cycleId);

        @Modifying
        @Query(value = "WITH T1 AS (" +
                        "   SELECT id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota, " +
                        "          round(avg(peso), 2) AS peso_tn " +
                        "   FROM virtus.produtos_elementos " +
                        "   WHERE (peso IS NOT NULL and peso <> 0) " +
                        "   GROUP BY id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota), " +
                        "T2 AS (" +
                        "   SELECT id_entidade, id_ciclo, id_pilar, id_componente, id_plano, " +
                        "          SUM(peso_tn) AS soma_pesos_tipos_notas " +
                        "   FROM T1 " +
                        "   GROUP BY id_entidade, id_ciclo, id_pilar, id_componente, id_plano), " +
                        "T3 AS (" +
                        "   SELECT T1.id_entidade, T1.id_ciclo, T1.id_pilar, T1.id_componente, T1.id_plano, T1.id_tipo_nota, "
                        +
                        "          T1.peso_tn, T2.soma_pesos_tipos_notas, " +
                        "          round(T1.peso_tn*100/T2.soma_pesos_tipos_notas, 2) AS ponderacao_tipo " +
                        "   FROM T1 " +
                        "   INNER JOIN T2 ON (T1.id_entidade = T2.id_entidade AND T1.id_ciclo = T2.id_ciclo " +
                        "                     AND T1.id_pilar = T2.id_pilar AND T1.id_componente = T2.id_componente " +
                        "                     AND T1.id_plano = T2.id_plano) " +
                        "   GROUP BY T1.id_entidade, T1.id_ciclo, T1.id_pilar, T1.id_componente, T1.id_plano, " +
                        "            T1.id_tipo_nota, T1.peso_tn, T2.soma_pesos_tipos_notas), " +
                        "T4 AS (" +
                        "   SELECT T3.id_entidade, T3.id_ciclo, T3.id_pilar, T3.id_componente, T3.id_plano, ponderacao_tipo, "
                        +
                        "          peso_tn, soma_pesos_tipos_notas, ponderacao_tipo*peso_tn/100 AS peso_plano, p.peso/100 AS ponderacao_plano "
                        +
                        "   FROM T3 " +
                        "   INNER JOIN virtus.produtos_planos p ON (p.id_entidade = T3.id_entidade AND p.id_ciclo = T3.id_ciclo "
                        +
                        "                                          AND p.id_pilar = T3.id_pilar AND p.id_componente = T3.id_componente "
                        +
                        "                                          AND p.id_plano = T3.id_plano)), " +
                        "T5 AS (" +
                        "   SELECT T4.id_entidade, T4.id_ciclo, T4.id_pilar, T4.id_componente, " +
                        "          sum(peso_plano*ponderacao_plano) AS peso_componente " +
                        "   FROM T4 " +
                        "   GROUP BY T4.id_entidade, T4.id_ciclo, T4.id_pilar, T4.id_componente) " +
                        "UPDATE virtus.produtos_componentes " +
                        "SET peso = (SELECT DISTINCT round(T5.peso_componente, 2) " +
                        "           FROM T5 " +
                        "           WHERE id_entidade = virtus.produtos_componentes.id_entidade " +
                        "             AND id_ciclo = virtus.produtos_componentes.id_ciclo " +
                        "             AND id_pilar = virtus.produtos_componentes.id_pilar " +
                        "             AND id_componente = virtus.produtos_componentes.id_componente) " +
                        "WHERE id_entidade = :entityId AND id_ciclo = :cycleId AND id_pilar = :pilarId AND id_componente = :componenteId", nativeQuery = true)
        int atualizarPesoComponente(
                        @Param("entityId") Integer entityId,
                        @Param("cycleId") Integer cycleId,
                        @Param("pilarId") Integer pilarId,
                        @Param("componenteId") Integer componenteId);

        @Modifying
        @Query(value = " WITH T1 AS (" +
                        "   SELECT id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota, " +
                        "          round(avg(peso), 2) AS peso_tn " +
                        "   FROM virtus.produtos_elementos " +
                        "   WHERE peso <> 0 " +
                        "   GROUP BY id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota), " +
                        " T2 AS (" +
                        "   SELECT id_entidade, id_ciclo, id_pilar, id_componente, id_plano, " +
                        "          SUM(peso_tn) AS soma_pesos_tipos_notas " +
                        "   FROM T1 " +
                        "   GROUP BY id_entidade, id_ciclo, id_pilar, id_componente, id_plano), " +
                        " T3 AS (" +
                        "   SELECT T1.id_entidade, T1.id_ciclo, T1.id_pilar, T1.id_componente, T1.id_plano, " +
                        "          T1.id_tipo_nota, T1.peso_tn, T2.soma_pesos_tipos_notas, " +
                        "          round(T1.peso_tn*100/T2.soma_pesos_tipos_notas, 2) AS ponderacao_tipo " +
                        "   FROM T1 " +
                        "   INNER JOIN T2 ON (T1.id_entidade = T2.id_entidade AND T1.id_ciclo = T2.id_ciclo " +
                        "                     AND T1.id_pilar = T2.id_pilar AND T1.id_componente = T2.id_componente " +
                        "                     AND T1.id_plano = T2.id_plano) " +
                        "   GROUP BY T1.id_entidade, T1.id_ciclo, T1.id_pilar, T1.id_componente, T1.id_plano, " +
                        "            T1.id_tipo_nota, T1.peso_tn, T2.soma_pesos_tipos_notas), " +
                        " T4 AS (" +
                        "   SELECT T3.id_entidade, T3.id_ciclo, T3.id_pilar, T3.id_componente, T3.id_plano, " +
                        "          SUM(ponderacao_tipo*peso_tn/100) AS total_peso_plano " +
                        "   FROM T3 " +
                        "   INNER JOIN virtus.produtos_planos p ON (p.id_entidade = T3.id_entidade AND p.id_ciclo = T3.id_ciclo "
                        +
                        "                                          AND p.id_pilar = T3.id_pilar AND p.id_componente = T3.id_componente "
                        +
                        "                                          AND p.id_plano = T3.id_plano) " +
                        "   GROUP BY T3.id_entidade, T3.id_ciclo, T3.id_pilar, T3.id_componente, T3.id_plano), " +
                        " T5 AS (" +
                        "   SELECT T4.id_entidade, T4.id_ciclo, T4.id_pilar, T4.id_componente, T4.id_plano, " +
                        "          p.peso/100 AS ponderacao_plano " +
                        "   FROM virtus.produtos_planos p " +
                        "   INNER JOIN T4 ON (T4.id_entidade = p.id_entidade AND T4.id_ciclo = p.id_ciclo " +
                        "                     AND T4.id_pilar = p.id_pilar AND T4.id_componente = p.id_componente " +
                        "                     AND T4.id_plano = p.id_plano) " +
                        "   GROUP BY T4.id_entidade, T4.id_ciclo, T4.id_pilar, T4.id_componente, T4.id_plano, p.peso), "
                        +
                        " T6 AS (" +
                        "   SELECT T4.id_entidade, T4.id_ciclo, T4.id_pilar, T4.id_componente, " +
                        "          sum(T4.total_peso_plano * T5.ponderacao_plano) AS denominador " +
                        "   FROM T4 " +
                        "   INNER JOIN T5 ON (T4.id_entidade = T5.id_entidade AND T4.id_ciclo = T5.id_ciclo " +
                        "                     AND T4.id_pilar = T5.id_pilar AND T4.id_componente = T5.id_componente " +
                        "                     AND T4.id_plano = T5.id_plano) " +
                        "   GROUP BY T4.id_entidade, T4.id_ciclo, T4.id_pilar, T4.id_componente), " +
                        " T7 AS (" +
                        "   SELECT p.id_entidade, p.id_ciclo, p.id_pilar, p.id_componente, " +
                        "          sum(p.nota * T4.total_peso_plano * T5.ponderacao_plano)/T6.denominador AS nota_componente "
                        +
                        "   FROM virtus.produtos_planos p " +
                        "   INNER JOIN T4 ON (T4.id_entidade = p.id_entidade AND T4.id_ciclo = p.id_ciclo " +
                        "                     AND T4.id_pilar = p.id_pilar AND T4.id_componente = p.id_componente " +
                        "                     AND T4.id_plano = p.id_plano) " +
                        "   INNER JOIN T5 ON (T4.id_entidade = T5.id_entidade AND T4.id_ciclo = T5.id_ciclo " +
                        "                     AND T4.id_pilar = T5.id_pilar AND T4.id_componente = T5.id_componente " +
                        "                     AND T4.id_plano = T5.id_plano) " +
                        "   INNER JOIN T6 ON (T6.id_entidade = T5.id_entidade AND T6.id_ciclo = T5.id_ciclo " +
                        "                     AND T6.id_pilar = T5.id_pilar AND T6.id_componente = T5.id_componente) " +
                        "   GROUP BY p.id_entidade, p.id_ciclo, p.id_pilar, p.id_componente, T6.denominador) " +
                        " UPDATE virtus.produtos_componentes " +
                        " SET nota = ( " +
                        "   SELECT round(T7.nota_componente,2) " +
                        "   FROM T7 " +
                        "   WHERE produtos_componentes.id_componente = T7.id_componente " +
                        "     AND produtos_componentes.id_pilar = T7.id_pilar " +
                        "     AND produtos_componentes.id_ciclo = T7.id_ciclo " +
                        "     AND produtos_componentes.id_entidade = T7.id_entidade " +
                        "   GROUP BY T7.id_entidade, T7.id_ciclo, T7.id_pilar, T7.id_componente, T7.nota_componente) " +
                        " WHERE produtos_componentes.id_entidade = :entityId " +
                        "   AND produtos_componentes.id_ciclo = :cycleId " +
                        "   AND produtos_componentes.id_pilar = :pillarId " +
                        "   AND produtos_componentes.id_componente = :componentId", nativeQuery = true)
        int atualizarComponenteNota(
                        @Param("entityId") Integer entityId,
                        @Param("cycleId") Integer cycleId,
                        @Param("pillarId") Integer pillarId,
                        @Param("componentId") Integer componentId);

        @Modifying
        @Transactional
        @Query(value = "UPDATE virtus.produtos_componentes SET id_auditor = :novoAuditorId, justificativa = :motivacao WHERE id_entidade = :entidadeId AND id_ciclo = :cicloId AND id_pilar = :pilarId AND id_componente = :componenteId", nativeQuery = true)
        int updateNewAuditorComponent(
                        @Param("entidadeId") Integer entidadeId,
                        @Param("cicloId") Integer cicloId,
                        @Param("pilarId") Integer pilarId,
                        @Param("componenteId") Integer componenteId,
                        @Param("novoAuditorId") Integer novoAuditorId,
                        @Param("motivacao") String motivacao);

        @Modifying
        @Transactional
        @Query(value = "UPDATE virtus.produtos_componentes SET inicia_em = :novaData, motivacao_reprogramacao = :motivacao WHERE id_entidade = :entidadeId AND id_ciclo = :cicloId AND id_pilar = :pilarId AND id_componente = :componenteId", nativeQuery = true)
        int updateStartsAtComponent(
                        @Param("entidadeId") Long entidadeId,
                        @Param("cicloId") Long cicloId,
                        @Param("pilarId") Long pilarId,
                        @Param("componenteId") Long componenteId,
                        @Param("novaData") LocalDate novaData,
                        @Param("motivacao") String motivacao);

        @Modifying
        @Transactional
        @Query(value = "UPDATE virtus.produtos_componentes SET termina_em = :novaData, motivacao_reprogramacao = :motivacao WHERE id_entidade = :entidadeId AND id_ciclo = :cicloId AND id_pilar = :pilarId AND id_componente = :componenteId", nativeQuery = true)
        int updateEndsAtComponent(
                        @Param("entidadeId") Long entidadeId,
                        @Param("cicloId") Long cicloId,
                        @Param("pilarId") Long pilarId,
                        @Param("componenteId") Long componenteId,
                        @Param("novaData") LocalDate novaData,
                        @Param("motivacao") String motivacao);

        @Query(value = "SELECT analise FROM virtus.produtos_componentes WHERE id_entidade = :entidadeId AND id_ciclo = :cicloId AND id_pilar = :pilarId AND id_componente = :componenteId", nativeQuery = true)
        String findByCycleLevelIds(Long entidadeId, Long cicloId, Long pilarId,
                        Long componenteId);

}