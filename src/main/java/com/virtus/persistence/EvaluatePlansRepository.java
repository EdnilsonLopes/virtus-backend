package com.virtus.persistence;

import static com.virtus.persistence.bigqueries.EvaluatePlansTreeQuery.EVALUATE_PLANS_TREE_QUERY;

import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.virtus.domain.dto.CurrentGradesDTO;
import com.virtus.domain.dto.CurrentWeightsDTO;
import com.virtus.domain.dto.request.ProductElementItemRequestDTO;
import com.virtus.domain.dto.request.ProductElementRequestDTO;
import com.virtus.domain.dto.request.ProductPillarRequestDTO;
import com.virtus.domain.dto.request.ProductPlanRequestDTO;
import com.virtus.domain.model.CurrentUser;
import com.virtus.domain.model.EvaluatePlansConsultModel;

@Repository
public class EvaluatePlansRepository {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        private static final Logger log = LoggerFactory.getLogger(EvaluatePlansRepository.class);

        public List<EvaluatePlansConsultModel> findPlansByEntityAndCycle(Integer entidadeId, Integer cicloId) {
                String sql = EVALUATE_PLANS_TREE_QUERY;
                log.info(sql
                                + " - entidadeId: {} cicloId: {}",
                                entidadeId, cicloId);
                return jdbcTemplate.query(sql, new Object[] { entidadeId, cicloId },
                                (rs, rowNum) -> {
                                        EvaluatePlansConsultModel evaluatePlan = EvaluatePlansConsultModel.builder()
                                                        .entidadeId(rs.getInt("id_entidade"))
                                                        .entidadeNome(rs.getString("entidade_nome"))
                                                        .cicloId(rs.getInt("id_ciclo"))
                                                        .cicloNome(rs.getString("ciclo_nome"))
                                                        .cicloNota(rs.getBigDecimal("ciclo_nota"))
                                                        .pilarId(rs.getInt("id_pilar"))
                                                        .pilarNome(rs.getString("pilar_nome"))
                                                        .pilarPeso(rs.getBigDecimal("pilar_peso"))
                                                        .pilarNota(rs.getBigDecimal("pilar_nota"))
                                                        .componenteId(rs.getInt("id_componente"))
                                                        .componenteNome(rs.getString("componente_nome"))
                                                        .componentePeso(rs.getBigDecimal("componente_peso"))
                                                        .componenteNota(rs.getBigDecimal("componente_nota"))
                                                        .supervisorId(rs.getInt("super_id"))
                                                        .supervisorNome(rs.getString("supervisor_nome"))
                                                        .auditorId(rs.getInt("auditor_id"))
                                                        .tipoNotaId(rs.getInt("id_tipo_nota"))
                                                        .tipoNotaLetra(rs.getString("letra"))
                                                        .tipoNotaCorLetra(rs.getString("cor_letra"))
                                                        .tipoNotaNome(rs.getString("nome"))
                                                        .tipoNotaPeso(rs.getBigDecimal("tipo_nota_peso"))
                                                        .tipoNotaNota(rs.getBigDecimal("tipo_nota_nota"))
                                                        .elementoId(rs.getInt("id_elemento"))
                                                        .elementoNome(rs.getString("elemento_nome"))
                                                        .elementoPeso(rs.getBigDecimal("elemento_peso"))
                                                        .elementoNota(rs.getBigDecimal("elemento_nota"))
                                                        .tipoPontuacaoId(rs.getInt("id_tipo_pontuacao"))
                                                        .pesoPadraoEC(rs.getBigDecimal("peso_padrao"))
                                                        .tipoMediaCPId(rs.getInt("tipo_media"))
                                                        .pesoPadraoCP(rs.getBigDecimal("peso_padrao_cp"))
                                                        .tipoMediaPCId(rs.getInt("tipo_media_pc"))
                                                        .pesoPadraoPC(rs.getBigDecimal("peso_padrao_pc"))
                                                        .itemId(rs.getInt("id_item"))
                                                        .itemNome(rs.getString("item_nome"))
                                                        .planoId(rs.getInt("id_plano"))
                                                        .cnpb(rs.getString("cnpb"))
                                                        // .recursoGarantidor(rs.getString("recurso_garantidor"))
                                                        // .planoModalidade(rs.getString("plano_modalidade"))
                                                        .planoPeso(rs.getBigDecimal("plano_peso"))
                                                        .planoNota(rs.getBigDecimal("plano_nota"))
                                                        .iniciaEm(rs.getString("inicia_em"))
                                                        .terminaEm(rs.getString("termina_em"))
                                                        .cStatus(rs.getString("cstatus"))
                                                        .statusId(rs.getInt("id_status"))
                                                        .produtoComponenteId(rs.getInt("id_produto_componente"))
                                                        .periodoPermitido(rs.getBoolean("periodo_permitido"))
                                                        .periodoCiclo(rs.getBoolean("periodo_ciclo"))
                                                        .cicloAnalisado(rs.getBoolean("ciclo_analisado"))
                                                        .pilarAnalisado(rs.getBoolean("pilar_analisado"))
                                                        .componenteAnalisado(rs.getBoolean("componente_analisado"))
                                                        .planoAnalisado(rs.getBoolean("plano_analisado"))
                                                        .tipoNotaAnalisado(rs.getBoolean("tipo_nota_analisado"))
                                                        .elementoAnalisado(rs.getBoolean("elemento_analisado"))
                                                        .itemAnalisado(rs.getBoolean("item_analisado"))
                                                        .cicloDescrito(rs.getBoolean("ciclo_descrito"))
                                                        .pilarDescrito(rs.getBoolean("pilar_descrito"))
                                                        .componenteDescrito(rs.getBoolean("componente_descrito"))
                                                        .planoDescrito(rs.getBoolean("plano_descrito"))
                                                        .tipoNotaDescrito(rs.getBoolean("tipo_nota_descrito"))
                                                        .elementoDescrito(rs.getBoolean("elemento_descrito"))
                                                        .itemDescrito(rs.getBoolean("item_descrito"))
                                                        .build();
                                        return evaluatePlan;
                                });
        }

        public void updateElementGrade(ProductElementRequestDTO dto, CurrentUser currentUser) {
                String sql = "UPDATE virtus.produtos_elementos SET " +
                                "nota = ?, " +
                                "motivacao_nota = ?, " +
                                "id_tipo_pontuacao = ( " +
                                "SELECT DISTINCT " +
                                "CASE " +
                                "WHEN pc.id_supervisor = ? THEN 1 " +
                                "WHEN 2 = ? THEN 3 " +
                                "ELSE 0 " +
                                "END " +
                                "FROM virtus.produtos_componentes pc " +
                                "WHERE pc.id_entidade = ? " +
                                "AND pc.id_ciclo = ? " +
                                "AND pc.id_pilar = ? " +
                                "AND pc.id_componente = ? " +
                                ") " +
                                "WHERE " +
                                "id_entidade = ? " +
                                "AND id_ciclo = ? " +
                                "AND id_pilar = ? " +
                                "AND id_plano = ? " +
                                "AND id_componente = ? " +
                                "AND id_elemento = ? " +
                                "AND nota <> ?";
                jdbcTemplate.update(sql,
                                dto.getNota(),
                                dto.getMotivacao(),
                                currentUser.getId(),
                                currentUser.getRoleId(),
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getComponenteId(),
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getPlanoId(),
                                dto.getComponenteId(),
                                dto.getElementoId(),
                                dto.getNota());
        }

        public void updateGradeTypeWeights(ProductElementRequestDTO dto, CurrentUser currentUser) {
                String sql = "WITH R1 AS (" +
                                "    SELECT id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota," +
                                "           ROUND(SUM(peso), 2) AS TOTAL" +
                                "    FROM virtus.produtos_elementos" +
                                "    GROUP BY id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota" +
                                ")," +
                                "R2 AS (" +
                                "    SELECT id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota," +
                                "           COUNT(1) AS CONTADOR" +
                                "    FROM virtus.produtos_elementos" +
                                "    WHERE peso <> 0" +
                                "    GROUP BY id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota" +
                                ")," +
                                "TMP AS (" +
                                "    SELECT r1.id_entidade, r1.id_ciclo, r1.id_pilar, r1.id_plano, r1.id_componente, r1.id_tipo_nota,"
                                +
                                "           CASE WHEN r2.CONTADOR IS NULL THEN 0 ELSE ROUND((r1.TOTAL / r2.CONTADOR), 2) END AS PONDERACAO"
                                +
                                "    FROM R1" +
                                "    LEFT JOIN R2" +
                                "           ON r1.id_entidade = r2.id_entidade" +
                                "          AND r1.id_ciclo = r2.id_ciclo" +
                                "          AND r1.id_pilar = r2.id_pilar" +
                                "          AND r1.id_plano = r2.id_plano" +
                                "          AND r1.id_componente = r2.id_componente" +
                                "          AND r1.id_tipo_nota = r2.id_tipo_nota" +
                                ")," +
                                "T2 AS (" +
                                "    SELECT id_entidade, id_pilar, id_plano, id_ciclo, id_componente," +
                                "           SUM(PONDERACAO) AS TOTAL_PESOS_TNS" +
                                "    FROM TMP" +
                                "    GROUP BY id_entidade, id_pilar, id_plano, id_ciclo, id_componente" +
                                ")," +
                                "T1 AS (" +
                                "    SELECT A1.id_entidade, A1.id_ciclo, A1.id_pilar, A1.id_plano, A1.id_componente, A1.id_tipo_nota,"
                                +
                                "           CASE WHEN T2.TOTAL_PESOS_TNS = 0 THEN 0" +
                                "                ELSE ROUND((A1.PONDERACAO / T2.TOTAL_PESOS_TNS) * 100, 2)" +
                                "           END AS peso" +
                                "    FROM TMP A1" +
                                "    INNER JOIN T2 ON A1.id_entidade = T2.id_entidade" +
                                "                 AND A1.id_ciclo = T2.id_ciclo" +
                                "                 AND A1.id_pilar = T2.id_pilar" +
                                "                 AND A1.id_plano = T2.id_plano" +
                                "                 AND A1.id_componente = T2.id_componente" +
                                "    WHERE A1.id_entidade = ?" +
                                "      AND A1.id_ciclo = ?" +
                                "      AND A1.id_pilar = ?" +
                                "      AND A1.id_plano = ?" +
                                "      AND A1.id_componente = ?" +
                                ")" +
                                "UPDATE virtus.produtos_tipos_notas " +
                                "SET peso = ROUND(T1.peso, 2) " +
                                "FROM T1 " +
                                "WHERE produtos_tipos_notas.id_tipo_nota = T1.id_tipo_nota " +
                                "  AND produtos_tipos_notas.id_componente = T1.id_componente " +
                                "  AND produtos_tipos_notas.id_plano = T1.id_plano " +
                                "  AND produtos_tipos_notas.id_pilar = T1.id_pilar " +
                                "  AND produtos_tipos_notas.id_ciclo = T1.id_ciclo " +
                                "  AND produtos_tipos_notas.id_entidade = T1.id_entidade";

                jdbcTemplate.update(sql,
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getPlanoId(),
                                dto.getComponenteId());
        }

        public void updatePlanWeights(ProductElementRequestDTO dto, CurrentUser currentUser) {
                String sql = "WITH total AS ( " +
                                "    SELECT a.id_entidade, a.id_ciclo, a.id_pilar, a.id_componente, " +
                                "           SUM(p.recurso_garantidor) AS total " +
                                "    FROM virtus.produtos_planos a " +
                                "    INNER JOIN virtus.planos p " +
                                "        ON p.id_entidade = a.id_entidade " +
                                "       AND p.id_plano = a.id_plano " +
                                "    WHERE a.id_entidade = ? " +
                                "      AND a.id_ciclo = ? " +
                                "      AND a.id_pilar = ? " +
                                "      AND a.id_componente = ? " +
                                "    GROUP BY a.id_entidade, a.id_ciclo, a.id_pilar, a.id_componente " +
                                "), " +
                                "R2 AS ( " +
                                "    SELECT a.id_entidade, a.id_ciclo, a.id_pilar, a.id_plano, a.id_componente, " +
                                "           ROUND(p.recurso_garantidor / t.total, 2) * 100 AS peso_percentual " +
                                "    FROM virtus.produtos_planos a " +
                                "    INNER JOIN virtus.planos p " +
                                "        ON p.id_entidade = a.id_entidade " +
                                "       AND p.id_plano = a.id_plano " +
                                "    INNER JOIN total t " +
                                "        ON a.id_entidade = t.id_entidade " +
                                "       AND a.id_ciclo = t.id_ciclo " +
                                "       AND a.id_pilar = t.id_pilar " +
                                "       AND a.id_componente = t.id_componente " +
                                ") " +
                                "UPDATE virtus.produtos_planos " +
                                "SET peso = ROUND(R2.peso_percentual, 2) " +
                                "FROM R2 " +
                                "WHERE R2.id_entidade = produtos_planos.id_entidade " +
                                "  AND R2.id_ciclo = produtos_planos.id_ciclo " +
                                "  AND R2.id_pilar = produtos_planos.id_pilar " +
                                "  AND R2.id_componente = produtos_planos.id_componente " +
                                "  AND R2.id_plano = produtos_planos.id_plano";

                jdbcTemplate.update(sql,
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getComponenteId());
        }

        public void updateComponentWeights(ProductElementRequestDTO dto, CurrentUser currentUser) {
                String sql = "WITH T1 AS ( " +
                                "    SELECT id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota, " +
                                "           ROUND(AVG(peso), 2) AS peso_tn " +
                                "    FROM virtus.produtos_elementos " +
                                "    WHERE peso IS NOT NULL AND peso <> 0 " +
                                "    GROUP BY id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota " +
                                "), " +
                                "T2 AS ( " +
                                "    SELECT id_entidade, id_ciclo, id_pilar, id_componente, id_plano, " +
                                "           SUM(peso_tn) AS soma_pesos_tipos_notas " +
                                "    FROM T1 " +
                                "    GROUP BY id_entidade, id_ciclo, id_pilar, id_componente, id_plano " +
                                "), " +
                                "T3 AS ( " +
                                "    SELECT T1.id_entidade, T1.id_ciclo, T1.id_pilar, T1.id_componente, T1.id_plano, " +
                                "           T1.id_tipo_nota, T1.peso_tn, T2.soma_pesos_tipos_notas, " +
                                "           ROUND(T1.peso_tn * 100 / T2.soma_pesos_tipos_notas, 2) AS ponderacao_tipo "
                                +
                                "    FROM T1 " +
                                "    INNER JOIN T2 ON T1.id_entidade = T2.id_entidade " +
                                "                 AND T1.id_ciclo = T2.id_ciclo " +
                                "                 AND T1.id_pilar = T2.id_pilar " +
                                "                 AND T1.id_componente = T2.id_componente " +
                                "                 AND T1.id_plano = T2.id_plano " +
                                "    GROUP BY T1.id_entidade, T1.id_ciclo, T1.id_pilar, T1.id_componente, " +
                                "             T1.id_plano, T1.id_tipo_nota, T1.peso_tn, T2.soma_pesos_tipos_notas " +
                                "), " +
                                "T4 AS ( " +
                                "    SELECT p.id_entidade, p.id_ciclo, p.id_pilar, p.id_componente, p.id_plano, " +
                                "           ponderacao_tipo, peso_tn, soma_pesos_tipos_notas, " +
                                "           ponderacao_tipo * peso_tn / 100 AS peso_plano, " +
                                "           p.peso / 100 AS ponderacao_plano " +
                                "    FROM T3 " +
                                "    RIGHT JOIN virtus.produtos_planos p ON p.id_entidade = T3.id_entidade " +
                                "                                       AND p.id_ciclo = T3.id_ciclo " +
                                "                                       AND p.id_pilar = T3.id_pilar " +
                                "                                       AND p.id_componente = T3.id_componente " +
                                "                                       AND p.id_plano = T3.id_plano " +
                                "), " +
                                "T5 AS ( " +
                                "    SELECT T4.id_entidade, T4.id_ciclo, T4.id_pilar, T4.id_componente, " +
                                "           COALESCE(SUM(peso_plano * ponderacao_plano),1) AS peso_componente " +
                                "    FROM T4 " +
                                "    GROUP BY T4.id_entidade, T4.id_ciclo, T4.id_pilar, T4.id_componente " +
                                ") " +
                                "UPDATE virtus.produtos_componentes " +
                                "SET peso = ( " +
                                "    SELECT DISTINCT ROUND(T5.peso_componente, 2) " +
                                "    FROM T5 " +
                                "    WHERE T5.id_entidade = virtus.produtos_componentes.id_entidade " +
                                "      AND T5.id_ciclo = virtus.produtos_componentes.id_ciclo " +
                                "      AND T5.id_pilar = virtus.produtos_componentes.id_pilar " +
                                "      AND T5.id_componente = virtus.produtos_componentes.id_componente " +
                                ") " +
                                "WHERE id_entidade = ? " +
                                "  AND id_ciclo = ? " +
                                "  AND id_pilar = ? " +
                                "  AND id_componente = ?";

                jdbcTemplate.update(sql,
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getComponenteId());
        }

        public CurrentWeightsDTO loadCurrentWeights(ProductElementRequestDTO dto) {
                String sql = "SELECT " +
                                "  COALESCE(FORMAT(a.peso, 'N2'), '0.00') AS plano, " +
                                "  COALESCE(FORMAT(b.peso, 'N2'), '0.00') AS tipo_nota, " +
                                "  COALESCE(FORMAT(c.peso, 'N2'), '0.00') AS componente, " +
                                "  COALESCE(FORMAT(d.peso, 'N2'), '0.00') AS pilar " +
                                "FROM virtus.produtos_planos a " +
                                "LEFT JOIN virtus.produtos_tipos_notas b ON a.id_entidade = b.id_entidade " +
                                " AND a.id_ciclo = b.id_ciclo AND a.id_pilar = b.id_pilar " +
                                " AND a.id_componente = b.id_componente AND a.id_plano = b.id_plano " +
                                "JOIN virtus.produtos_componentes c ON a.id_entidade = c.id_entidade " +
                                " AND a.id_ciclo = c.id_ciclo AND a.id_pilar = c.id_pilar AND a.id_componente = c.id_componente "
                                +
                                "JOIN virtus.produtos_pilares d ON a.id_entidade = d.id_entidade " +
                                " AND a.id_ciclo = d.id_ciclo AND a.id_pilar = d.id_pilar " +
                                "JOIN virtus.produtos_ciclos e ON a.id_entidade = e.id_entidade AND a.id_ciclo = e.id_ciclo "
                                +
                                "WHERE a.id_entidade = ? " +
                                " AND a.id_ciclo = ? " +
                                " AND a.id_pilar = ? " +
                                " AND a.id_componente = ? " +
                                " AND a.id_plano = ? " +
                                " AND (b.id_tipo_nota = ? OR b.id_tipo_nota IS NULL)";

                return jdbcTemplate.query(sql, ps -> {
                        ps.setLong(1, dto.getEntidadeId());
                        ps.setLong(2, dto.getCicloId());
                        ps.setLong(3, dto.getPilarId());
                        ps.setLong(4, dto.getComponenteId());
                        ps.setLong(5, dto.getPlanoId());
                        if (dto.getTipoNotaId() != null) {
                                ps.setLong(6, dto.getTipoNotaId()); // Se tipoNotaId não for null, passamos o valor.
                        } else {
                                ps.setNull(6, Types.NULL); // Caso tipoNotaId seja null, passamos NULL.
                        }
                }, rs -> {
                        if (rs.next()) {
                                return CurrentWeightsDTO.builder()
                                                .planoPeso(stripZeros(rs.getString("plano")))
                                                .componentePeso(stripZeros(rs.getString("componente")))
                                                .pilarPeso(stripZeros(rs.getString("pilar")))
                                                .tipoNotaPeso(stripZeros(rs.getString("tipo_nota")))
                                                .build();
                        } else {
                                return new CurrentWeightsDTO(); // empty if not found
                        }
                });
        }

        private String stripZeros(String value) {
                if (value == null)
                        return null;
                if (value.contains(".")) {
                        value = value.replaceAll("0*$", "").replaceAll("\\.$", "");
                }
                return value;
        }

        public void updateGradeTypeGrade(ProductElementRequestDTO dto) {
                String sql = "WITH T1 AS ( " +
                                "    SELECT id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota, " +
                                "           peso * nota AS produtos " +
                                "    FROM virtus.produtos_elementos " +
                                "), " +
                                "T2 AS ( " +
                                "    SELECT id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota, " +
                                "           SUM(peso) AS soma_pesos_elementos " +
                                "    FROM virtus.produtos_elementos " +
                                "    GROUP BY id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota " +
                                "), " +
                                "T3 AS ( " +
                                "    SELECT T1.id_entidade, T1.id_ciclo, T1.id_pilar, T1.id_componente, T1.id_plano, T1.id_tipo_nota, "
                                +
                                "           SUM(T1.produtos) / T2.soma_pesos_elementos AS nota_tn " +
                                "    FROM T1 " +
                                "    INNER JOIN T2 ON T1.id_entidade = T2.id_entidade " +
                                "                 AND T1.id_ciclo = T2.id_ciclo " +
                                "                 AND T1.id_pilar = T2.id_pilar " +
                                "                 AND T1.id_componente = T2.id_componente " +
                                "                 AND T1.id_plano = T2.id_plano " +
                                "                 AND T1.id_tipo_nota = T2.id_tipo_nota " +
                                "    WHERE T1.id_entidade = ? " +
                                "      AND T1.id_ciclo = ? " +
                                "      AND T1.id_pilar = ? " +
                                "      AND T1.id_componente = ? " +
                                "      AND T1.id_plano = ? " +
                                "    GROUP BY T1.id_entidade, T1.id_ciclo, T1.id_pilar, T1.id_plano, T1.id_componente, T1.id_tipo_nota, T2.soma_pesos_elementos "
                                +
                                ") " +
                                "UPDATE virtus.produtos_tipos_notas " +
                                "SET nota = ROUND(T3.nota_tn, 2) " +
                                "FROM T3 " +
                                "WHERE produtos_tipos_notas.id_tipo_nota = T3.id_tipo_nota " +
                                "  AND produtos_tipos_notas.id_componente = T3.id_componente " +
                                "  AND produtos_tipos_notas.id_plano = T3.id_plano " +
                                "  AND produtos_tipos_notas.id_pilar = T3.id_pilar " +
                                "  AND produtos_tipos_notas.id_ciclo = T3.id_ciclo " +
                                "  AND produtos_tipos_notas.id_entidade = T3.id_entidade";

                jdbcTemplate.update(sql,
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getComponenteId(),
                                dto.getPlanoId());
        }

        public void updatePlanGrade(ProductElementRequestDTO dto) {
                String sql = "UPDATE virtus.produtos_planos " +
                                "SET nota = ( " +
                                "  SELECT ROUND(SUM(nota * peso) / SUM(peso), 2) AS media " +
                                "  FROM virtus.produtos_tipos_notas b " +
                                "  WHERE produtos_planos.id_entidade = b.id_entidade " +
                                "    AND produtos_planos.id_ciclo = b.id_ciclo " +
                                "    AND produtos_planos.id_pilar = b.id_pilar " +
                                "    AND produtos_planos.id_componente = b.id_componente " +
                                "    AND produtos_planos.id_plano = b.id_plano " +
                                "  GROUP BY b.id_entidade, b.id_ciclo, b.id_pilar, b.id_plano, b.id_componente " +
                                "  HAVING SUM(peso) > 0 " +
                                ") " +
                                "WHERE id_entidade = ? " +
                                "  AND id_ciclo = ? " +
                                "  AND id_pilar = ? " +
                                "  AND id_componente = ? " +
                                "  AND id_plano = ?";

                jdbcTemplate.update(sql,
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getComponenteId(),
                                dto.getPlanoId());
        }

        public void updateComponentGrade(ProductElementRequestDTO dto) {
                String sql = "WITH T1 AS ( " +
                                "  SELECT id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota, " +
                                "         ROUND(AVG(peso), 2) AS peso_tn " +
                                "  FROM virtus.produtos_elementos " +
                                "  WHERE peso <> 0 " +
                                "  GROUP BY id_entidade, id_ciclo, id_pilar, id_plano, id_componente, id_tipo_nota " +
                                "), " +
                                "T2 AS ( " +
                                "  SELECT id_entidade, id_ciclo, id_pilar, id_componente, id_plano, SUM(peso_tn) AS soma_pesos_tipos_notas "
                                +
                                "  FROM T1 " +
                                "  GROUP BY id_entidade, id_ciclo, id_pilar, id_componente, id_plano " +
                                "), " +
                                "T3 AS ( " +
                                "  SELECT T1.id_entidade, T1.id_ciclo, T1.id_pilar, T1.id_componente, T1.id_plano, T1.id_tipo_nota, "
                                +
                                "         T1.peso_tn, T2.soma_pesos_tipos_notas, " +
                                "         ROUND(T1.peso_tn * 100 / T2.soma_pesos_tipos_notas, 2) AS ponderacao_tipo " +
                                "  FROM T1 " +
                                "  INNER JOIN T2 ON T1.id_entidade = T2.id_entidade " +
                                "               AND T1.id_ciclo = T2.id_ciclo " +
                                "               AND T1.id_pilar = T2.id_pilar " +
                                "               AND T1.id_componente = T2.id_componente " +
                                "               AND T1.id_plano = T2.id_plano " +
                                "  GROUP BY T1.id_entidade, T1.id_ciclo, T1.id_pilar, T1.id_componente, " +
                                "           T1.id_plano, T1.id_tipo_nota, T1.peso_tn, T2.soma_pesos_tipos_notas " +
                                "), " +
                                "T4 AS ( " +
                                "  SELECT p.id_entidade, p.id_ciclo, p.id_pilar, p.id_componente, p.id_plano, " +
                                "         SUM(ponderacao_tipo * peso_tn / 100) AS total_peso_plano " +
                                "  FROM T3 " +
                                "  RIGHT JOIN virtus.produtos_planos p ON p.id_entidade = T3.id_entidade " +
                                "                                     AND p.id_ciclo = T3.id_ciclo " +
                                "                                     AND p.id_pilar = T3.id_pilar " +
                                "                                     AND p.id_componente = T3.id_componente " +
                                "                                     AND p.id_plano = T3.id_plano " +
                                "  GROUP BY p.id_entidade, p.id_ciclo, p.id_pilar, p.id_componente, p.id_plano " +
                                "), " +
                                "T5 AS ( " +
                                "  SELECT p.id_entidade, p.id_ciclo, p.id_pilar, p.id_componente, p.id_plano, p.peso / 100 AS ponderacao_plano "
                                +
                                "  FROM virtus.produtos_planos p " +
                                "  LEFT JOIN T4 ON T4.id_entidade = p.id_entidade " +
                                "               AND T4.id_ciclo = p.id_ciclo " +
                                "               AND T4.id_pilar = p.id_pilar " +
                                "               AND T4.id_componente = p.id_componente " +
                                "               AND T4.id_plano = p.id_plano " +
                                "  GROUP BY p.id_entidade, p.id_ciclo, p.id_pilar, p.id_componente, p.id_plano, p.peso "
                                +
                                "), " +
                                "T6 AS ( " +
                                "  SELECT T5.id_entidade, T5.id_ciclo, T5.id_pilar, T5.id_componente, " +
                                "         SUM(COALESCE(T4.total_peso_plano,1) * T5.ponderacao_plano) AS denominador " +
                                "  FROM T4 " +
                                "  LEFT JOIN T5 ON T4.id_entidade = T5.id_entidade " +
                                "               AND T4.id_ciclo = T5.id_ciclo " +
                                "               AND T4.id_pilar = T5.id_pilar " +
                                "               AND T4.id_componente = T5.id_componente " +
                                "               AND T4.id_plano = T5.id_plano " +
                                "  GROUP BY T5.id_entidade, T5.id_ciclo, T5.id_pilar, T5.id_componente " +
                                "), " +
                                "T7 AS ( " +
                                "  SELECT p.id_entidade, p.id_ciclo, p.id_pilar, p.id_componente, " +
                                "         SUM(p.nota * COALESCE(T4.total_peso_plano,1) * T5.ponderacao_plano) / T6.denominador AS nota_componente "
                                +
                                "  FROM virtus.produtos_planos p " +
                                "  INNER JOIN T4 ON T4.id_entidade = p.id_entidade " +
                                "               AND T4.id_ciclo = p.id_ciclo " +
                                "               AND T4.id_pilar = p.id_pilar " +
                                "               AND T4.id_componente = p.id_componente " +
                                "               AND T4.id_plano = p.id_plano " +
                                "  INNER JOIN T5 ON T4.id_entidade = T5.id_entidade " +
                                "               AND T4.id_ciclo = T5.id_ciclo " +
                                "               AND T4.id_pilar = T5.id_pilar " +
                                "               AND T4.id_componente = T5.id_componente " +
                                "               AND T4.id_plano = T5.id_plano " +
                                "  INNER JOIN T6 ON T6.id_entidade = T5.id_entidade " +
                                "               AND T6.id_ciclo = T5.id_ciclo " +
                                "               AND T6.id_pilar = T5.id_pilar " +
                                "               AND T6.id_componente = T5.id_componente " +
                                "  GROUP BY p.id_entidade, p.id_ciclo, p.id_pilar, p.id_componente, T6.denominador " +
                                ") " +
                                "UPDATE virtus.produtos_componentes " +
                                "SET nota = ( " +
                                "  SELECT ROUND(T7.nota_componente, 2) " +
                                "  FROM T7 " +
                                "  WHERE produtos_componentes.id_componente = T7.id_componente " +
                                "    AND produtos_componentes.id_pilar = T7.id_pilar " +
                                "    AND produtos_componentes.id_ciclo = T7.id_ciclo " +
                                "    AND produtos_componentes.id_entidade = T7.id_entidade " +
                                "  GROUP BY T7.id_entidade, T7.id_ciclo, T7.id_pilar, T7.id_componente, T7.nota_componente "
                                +
                                ") " +
                                "WHERE produtos_componentes.id_entidade = ? " +
                                "  AND produtos_componentes.id_ciclo = ? " +
                                "  AND produtos_componentes.id_pilar = ? " +
                                "  AND produtos_componentes.id_componente = ?";

                jdbcTemplate.update(sql,
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getComponenteId());
        }

        public void updatePillarGrade(ProductElementRequestDTO dto) {
                String sql = "UPDATE virtus.produtos_pilares " +
                                "SET nota = ( " +
                                "  SELECT ROUND(SUM(b.nota * b.peso) / SUM(b.peso), 2) AS media " +
                                "  FROM virtus.produtos_componentes b " +
                                "  WHERE produtos_pilares.id_entidade = b.id_entidade " +
                                "    AND produtos_pilares.id_ciclo = b.id_ciclo " +
                                "    AND produtos_pilares.id_pilar = b.id_pilar " +
                                "    AND b.nota IS NOT NULL AND b.nota <> 0 " +
                                "  GROUP BY b.id_entidade, b.id_ciclo, b.id_pilar " +
                                ") " +
                                "WHERE id_entidade = ? " +
                                "  AND id_ciclo = ? " +
                                "  AND id_pilar = ?";

                jdbcTemplate.update(sql,
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId());
        }

        public void updateCycleGrade(ProductElementRequestDTO dto) {
                String sql = "UPDATE virtus.produtos_ciclos " +
                                "SET nota = R.media " +
                                "FROM ( " +
                                "  SELECT ROUND(SUM(nota * peso / 100), 2) AS media " +
                                "  FROM virtus.produtos_pilares b " +
                                "  WHERE b.id_entidade = ? " +
                                "    AND b.id_ciclo = ? " +
                                "    AND b.nota IS NOT NULL AND b.nota <> 0 " +
                                ") R " +
                                "WHERE id_entidade = ? " +
                                "  AND id_ciclo = ?";

                jdbcTemplate.update(sql,
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getEntidadeId(),
                                dto.getCicloId());
        }

        public CurrentGradesDTO loadCurrentGrades(ProductElementRequestDTO dto) {
                String sql = "SELECT " +
                                "  COALESCE(FORMAT(a.nota, 'N2'), '0.00') AS plano, " +
                                "  COALESCE(FORMAT(b.nota, 'N2'), '0.00') AS tipo_nota, " +
                                "  COALESCE(FORMAT(c.nota, 'N2'), '0.00') AS componente, " +
                                "  COALESCE(FORMAT(d.nota, 'N2'), '0.00') AS pilar, " +
                                "  COALESCE(FORMAT(e.nota, 'N2'), '0.00') AS ciclo " +
                                "FROM virtus.produtos_planos a " +
                                "LEFT JOIN virtus.produtos_tipos_notas b ON a.id_entidade = b.id_entidade " +
                                " AND a.id_ciclo = b.id_ciclo " +
                                " AND a.id_pilar = b.id_pilar " +
                                " AND a.id_componente = b.id_componente " +
                                " AND a.id_plano = b.id_plano " +
                                "JOIN virtus.produtos_componentes c ON a.id_entidade = c.id_entidade " +
                                " AND a.id_ciclo = c.id_ciclo " +
                                " AND a.id_pilar = c.id_pilar " +
                                " AND a.id_componente = c.id_componente " +
                                "JOIN virtus.produtos_pilares d ON a.id_entidade = d.id_entidade " +
                                " AND a.id_ciclo = d.id_ciclo " +
                                " AND a.id_pilar = d.id_pilar " +
                                "JOIN virtus.produtos_ciclos e ON a.id_entidade = e.id_entidade " +
                                " AND a.id_ciclo = e.id_ciclo " +
                                "WHERE a.id_entidade = ? " +
                                "  AND a.id_ciclo = ? " +
                                "  AND a.id_pilar = ? " +
                                "  AND a.id_componente = ? " +
                                "  AND a.id_plano = ? " +
                                "  AND (b.id_tipo_nota = ? OR b.id_tipo_nota IS NULL)";

                return jdbcTemplate.query(sql, ps -> {
                        ps.setLong(1, dto.getEntidadeId());
                        ps.setLong(2, dto.getCicloId());
                        ps.setLong(3, dto.getPilarId());
                        ps.setLong(4, dto.getComponenteId());
                        ps.setLong(5, dto.getPlanoId());
                        if (dto.getTipoNotaId() != null) {
                                ps.setLong(6, dto.getTipoNotaId()); // Se tipoNotaId não for null, passamos o valor.
                        } else {
                                ps.setNull(6, Types.NULL); // Caso tipoNotaId seja null, passamos NULL.
                        }
                }, rs -> {
                        if (rs.next()) {
                                return CurrentGradesDTO.builder()
                                                .tipoNotaNota(stripZeros(rs.getString("tipo_nota")))
                                                .planoNota(stripZeros(rs.getString("plano")))
                                                .componenteNota(stripZeros(rs.getString("componente")))
                                                .pilarNota(stripZeros(rs.getString("pilar")))
                                                .cicloNota(stripZeros(rs.getString("ciclo")))
                                                .build();
                        }
                        return new CurrentGradesDTO(); // empty if not found
                });
        }

        public String updatePillarWeight(ProductPillarRequestDTO dto, CurrentUser currentUser) {
                String sqlUpdate = "UPDATE virtus.produtos_pilares SET peso = ?, motivacao_peso = ? " +
                                "WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ?";

                jdbcTemplate.update(sqlUpdate,
                                dto.getNovoPeso(),
                                dto.getMotivacao(),
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId());

                updateCycleGrade(dto);

                String sqlSelect = "SELECT ISNULL(FORMAT(e.nota, 'N', 'pt-BR'), '.00') AS ciclo " +
                                "FROM virtus.produtos_ciclos e " +
                                "WHERE e.id_entidade = ? AND e.id_ciclo = ?";

                String notaCiclo = jdbcTemplate.query(sqlSelect,
                                new Object[] { dto.getEntidadeId(), dto.getCicloId() },
                                rs -> rs.next() ? rs.getString("ciclo") : ".00");
                return notaCiclo;
        }

        private void updateCycleGrade(ProductPillarRequestDTO dto) {
                String sql = "UPDATE virtus.produtos_ciclos " +
                                "SET nota = R.media " +
                                "FROM ( " +
                                "  SELECT ROUND(SUM(nota * peso / 100), 2) AS media " +
                                "  FROM virtus.produtos_pilares " +
                                "  WHERE id_entidade = ? " +
                                "    AND id_ciclo = ? " +
                                "    AND nota <> 0 " +
                                "    AND nota IS NOT NULL " +
                                ") R " +
                                "WHERE id_entidade = ? " +
                                "  AND id_ciclo = ?";

                jdbcTemplate.update(sql, dto.getEntidadeId(), dto.getCicloId(), dto.getEntidadeId(),
                                dto.getCicloId());
        }

        public void updateElementWeight(ProductElementRequestDTO dto, CurrentUser currentUser) {
                String sql = "UPDATE virtus.produtos_elementos SET peso = ?, motivacao_peso = ? " +
                                " WHERE id_entidade = ? AND " +
                                " id_ciclo = ? AND " +
                                " id_pilar = ? AND " +
                                " id_plano = ? AND " +
                                " id_componente = ? AND " +
                                " id_elemento = ? ";
                jdbcTemplate.update(sql,
                                dto.getPeso(),
                                dto.getMotivacao(),
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getPlanoId(),
                                dto.getComponenteId(),
                                dto.getElementoId());
        }

        public void updateAnalysis(String objectType, ProductElementItemRequestDTO dto, CurrentUser currentUser) {
                if (objectType.equals("Ciclo")) {
                        String sql = "UPDATE virtus.produtos_ciclos SET analise = ? WHERE id_entidade = ? AND id_ciclo = ?";
                        jdbcTemplate.update(sql,
                                        dto.getAnalise(),
                                        dto.getEntidadeId(),
                                        dto.getCicloId());
                } else if (objectType.equals("Pilar")) {
                        String sql = "UPDATE virtus.produtos_pilares SET analise = ? WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ?";
                        jdbcTemplate.update(sql,
                                        dto.getAnalise(),
                                        dto.getEntidadeId(),
                                        dto.getCicloId(),
                                        dto.getPilarId());
                } else if (objectType.equals("Componente")) {
                        String sql = "UPDATE virtus.produtos_componentes SET analise = ? WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? AND id_componente = ?";
                        jdbcTemplate.update(sql,
                                        dto.getAnalise(),
                                        dto.getEntidadeId(),
                                        dto.getCicloId(),
                                        dto.getPilarId(),
                                        dto.getComponenteId());
                } else if (objectType.equals("Plano")) {
                        String sql = "UPDATE virtus.produtos_planos SET analise = ? WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? AND id_componente = ? AND id_plano = ?";
                        jdbcTemplate.update(sql,
                                        dto.getAnalise(),
                                        dto.getEntidadeId(),
                                        dto.getCicloId(),
                                        dto.getPilarId(),
                                        dto.getComponenteId(),
                                        dto.getPlanoId());
                } else if (objectType.equals("Tipo Nota")) {
                        String sql = "UPDATE virtus.produtos_tipos_notas SET analise = ? WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? AND id_componente = ? AND id_plano = ? AND id_tipo_nota = ? ";
                        jdbcTemplate.update(sql,
                                        dto.getAnalise(),
                                        dto.getEntidadeId(),
                                        dto.getCicloId(),
                                        dto.getPilarId(),
                                        dto.getComponenteId(),
                                        dto.getPlanoId(),
                                        dto.getTipoNotaId());
                } else if (objectType.equals("Elemento")) {
                        String sql = "UPDATE virtus.produtos_elementos SET analise = ? WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? AND id_componente = ? AND id_plano = ? AND id_tipo_nota = ? AND id_elemento = ?";
                        jdbcTemplate.update(sql,
                                        dto.getAnalise(),
                                        dto.getEntidadeId(),
                                        dto.getCicloId(),
                                        dto.getPilarId(),
                                        dto.getComponenteId(),
                                        dto.getPlanoId(),
                                        dto.getTipoNotaId(),
                                        dto.getElementoId());
                } else if (objectType.equals("Item")) {
                        String sql = "UPDATE virtus.produtos_itens SET analise = ? WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? AND id_componente = ? AND id_plano = ? AND id_tipo_nota = ? AND id_elemento = ? AND id_item = ?";
                        jdbcTemplate.update(sql,
                                        dto.getAnalise(),
                                        dto.getEntidadeId(),
                                        dto.getCicloId(),
                                        dto.getPilarId(),
                                        dto.getComponenteId(),
                                        dto.getPlanoId(),
                                        dto.getTipoNotaId(),
                                        dto.getElementoId(),
                                        dto.getItemId());
                }
        }

        public void updateRatifyNote(ProductElementRequestDTO requestDTO, CurrentUser currentUser) {
                // Query SQL para atualizar a nota e motivação
                String sql = "UPDATE virtus.produtos_planos SET nota = ?, motivacao_nota = ? " +
                                "WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? AND id_plano = ? AND id_componente = ?";

                // Executando a query com os parâmetros preenchidos
                jdbcTemplate.update(sql,
                                requestDTO.getNota(), // Preenchendo a nota
                                requestDTO.getMotivacao(), // Preenchendo a motivação
                                requestDTO.getEntidadeId(), // Preenchendo o id_entidade na cláusula WHERE
                                requestDTO.getCicloId(), // Preenchendo o id_ciclo
                                requestDTO.getPilarId(), // Preenchendo o id_pilar
                                requestDTO.getPlanoId(), // Preenchendo o id_plano
                                requestDTO.getComponenteId()); // Preenchendo o id_componente na cláusula WHERE
        }

        // Método para retificar a nota
        public void updateRectifyNote(ProductElementRequestDTO requestDTO, CurrentUser currentUser) {
                String sql = "UPDATE virtus.produtos_planos SET nota = ?, motivacao_nota = ? " +
                                "WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? AND id_plano = ? AND id_componente = ?";

                jdbcTemplate.update(sql,
                                requestDTO.getNota(),
                                requestDTO.getMotivacao(),
                                requestDTO.getEntidadeId(),
                                requestDTO.getCicloId(),
                                requestDTO.getPilarId(),
                                requestDTO.getPlanoId(),
                                requestDTO.getComponenteId());
        }

        public void registerHistory(ProductElementRequestDTO requestDTO, String tipoAlteracao,
                        CurrentUser currentUser) {
                // Query SQL para registrar o histórico da alteração
                String sql = "INSERT INTO virtus.produtos_planos_historicos(" +
                                "id_entidade, id_ciclo, id_pilar, id_plano, id_componente, " +
                                "id_tipo_pontuacao, peso, nota, tipo_alteracao, motivacao_nota, " +
                                "id_author, criado_em, id_versao_origem, id_status) " +
                                "SELECT id_entidade, id_ciclo, id_pilar, id_plano, id_componente, " +
                                "id_tipo_pontuacao, peso, nota, ?, motivacao_nota, " +
                                "? , GETDATE(), id_author, id_status " +
                                "FROM virtus.produtos_planos " +
                                "WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? " +
                                "AND id_plano = ? AND id_componente = ?";

                jdbcTemplate.update(sql,
                                tipoAlteracao,
                                currentUser.getId(),
                                requestDTO.getEntidadeId(),
                                requestDTO.getCicloId(),
                                requestDTO.getPilarId(),
                                requestDTO.getPlanoId(),
                                requestDTO.getComponenteId());
        }

}
