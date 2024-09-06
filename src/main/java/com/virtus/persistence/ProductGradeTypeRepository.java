package com.virtus.persistence;

import com.virtus.domain.model.CurrentUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductGradeTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductGradeTypeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int inserirProdutosTiposNotas(Integer entidadeId, Integer cicloId, Integer pilarId, Integer componenteId, Integer idPlano, Integer tipoPontuacao, Integer authorId) {
        String sql = "INSERT INTO virtus.produtos_tipos_notas ( " +
                " id_entidade, " +
                " id_ciclo, " +
                " id_pilar, " +
                " id_componente, " +
                " id_plano, " +
                " id_tipo_nota, " +
                " nota, " +
                " id_tipo_pontuacao, " +
                " id_author, " +
                " criado_em ) " +
                " SELECT " +
                " p.id_entidade, " +
                " p.id_ciclo, " +
                " p.id_pilar, " +
                " p.id_componente, " +
                " p.id_plano, " +
                " d.id_tipo_nota, " +
                " 0, ?, ?, GETDATE() " +
                " FROM virtus.pilares_ciclos a " +
                " LEFT JOIN virtus.COMPONENTES_PILARES b ON a.id_pilar = b.id_pilar " +
                " LEFT JOIN virtus.TIPOS_NOTAS_COMPONENTES d ON b.id_componente = d.id_componente " +
                " LEFT JOIN virtus.PRODUTOS_PLANOS p ON (b.id_componente = p.id_componente AND a.id_ciclo = p.id_ciclo AND p.id_pilar = b.id_pilar) " +
                " WHERE a.id_ciclo = ? " +
                "   AND p.id_entidade = ? " +
                "   AND p.id_pilar = ? " +
                "   AND p.id_componente = ? " +
                "   AND p.id_plano = ? " +
                "   AND d.id_tipo_nota IS NOT NULL " +
                "   AND NOT EXISTS " +
                "     (SELECT 1 FROM virtus.produtos_tipos_notas e " +
                "      WHERE e.id_entidade = ? " +
                "        AND e.id_ciclo = a.id_ciclo " +
                "        AND e.id_pilar = a.id_pilar " +
                "        AND e.id_plano = ? " +
                "        AND e.id_tipo_nota = d.id_tipo_nota " +
                "        AND e.id_componente = b.id_componente) " +
                " GROUP BY p.id_entidade, p.id_ciclo, p.id_pilar, p.id_componente, p.id_plano, d.id_tipo_nota " +
                " ORDER BY 1,2,3,4,5,6";

        return jdbcTemplate.update(sql, tipoPontuacao, authorId, cicloId, entidadeId, pilarId, componenteId, idPlano, entidadeId, idPlano);
    }

    public int atualizarPesosTiposNotas(Integer entidadeId, Integer cicloId, Integer pilarId, Integer componenteId, Integer planoId) {
        String sql = "WITH R1 AS  " +
                "  (SELECT id_entidade, " +
                "                  id_ciclo, " +
                "                  id_pilar, " +
                "                  id_plano, " +
                "                  id_componente, " +
                "                  id_tipo_nota, " +
                "                  round(sum(peso), 2) AS TOTAL " +
                "           FROM virtus.produtos_elementos " +
                "           GROUP BY id_entidade, " +
                "                    id_ciclo, " +
                "                    id_pilar, " +
                "                    id_plano, " +
                "                    id_componente, " +
                "                    id_tipo_nota), " +
                "  R2 AS (SELECT id_entidade, " +
                "                  id_ciclo, " +
                "                  id_pilar, " +
                "                  id_plano, " +
                "                  id_componente, " +
                "                  id_tipo_nota, " +
                "                  count(1) AS CONTADOR " +
                "           FROM virtus.produtos_elementos " +
                "           WHERE peso <> 0 " +
                "           GROUP BY id_entidade, " +
                "                    id_ciclo, " +
                "                    id_pilar, " +
                "                    id_plano, " +
                "                    id_componente, " +
                "                    id_tipo_nota), " +
                "  TMP AS " +
                "       (SELECT r1.id_entidade, " +
                "               r1.id_ciclo, " +
                "               r1.id_pilar, " +
                "               r1.id_plano, " +
                "               r1.id_componente, " +
                "               r1.id_tipo_nota, " +
                "               CASE WHEN r2.contador IS NULL THEN 0 ELSE round((r1.TOTAL/r2.contador), 2) END AS PONDERACAO " +
                "        FROM R1         " +
                "        LEFT JOIN R2 " +
                "  		ON (r1.id_entidade = r2.id_entidade " +
                "  		AND r1.id_ciclo = r2.id_ciclo " +
                "  		AND r1.id_pilar = r2.id_pilar " +
                "  		AND r1.id_plano = r2.id_plano " +
                "  		AND r1.id_componente = r2.id_componente " +
                "  		AND r1.id_tipo_nota = r2.id_tipo_nota)), " +
                "  T2 AS (SELECT id_entidade, " +
                "               id_pilar, " +
                "               id_plano, " +
                "               id_ciclo, " +
                "               id_componente, " +
                "               SUM(PONDERACAO) AS TOTAL_PESOS_TNS " +
                "        FROM TMP " +
                "        GROUP BY id_entidade, id_pilar, id_plano, id_ciclo, id_componente), " +
                "  T1 AS (SELECT A1.id_entidade, " +
                "  	A1.id_ciclo, " +
                "  	A1.id_pilar, " +
                "  	A1.id_plano,                             " +
                "  	A1.id_componente, " +
                "  	A1.id_tipo_nota, " +
                "  	CASE WHEN T2.total_pesos_tns = 0 THEN 0 ELSE round((A1.PONDERACAO/T2.total_pesos_tns)*100, 2) END AS peso " +
                "  	   FROM TMP A1 " +
                "  	   INNER JOIN T2 ON (A1.id_entidade = T2.id_entidade " +
                "  							 AND A1.id_pilar = T2.id_pilar " +
                "  							 AND A1.id_plano = T2.id_plano " +
                "  							 AND A1.id_ciclo = T2.id_ciclo " +
                "  							 AND A1.id_componente = T2.id_componente) " +
                "  	   WHERE A1.id_componente = ? " +
                "  		 AND A1.id_pilar = ? " +
                "  		 AND A1.id_plano = ? " +
                "  		 AND A1.id_ciclo = ? " +
                "  		 AND A1.id_entidade = ?) " +
                "  UPDATE virtus.produtos_tipos_notas  " +
                "  SET peso = round(T1.peso,2) FROM T1 " +
                "  WHERE produtos_tipos_notas.id_tipo_nota = T1.id_tipo_nota " +
                "    AND produtos_tipos_notas.id_componente = T1.id_componente " +
                "    AND produtos_tipos_notas.id_plano = T1.id_plano " +
                "    AND produtos_tipos_notas.id_pilar = T1.id_pilar " +
                "    AND produtos_tipos_notas.id_ciclo = T1.id_ciclo " +
                "    AND produtos_tipos_notas.id_entidade = T1.id_entidade";

        return jdbcTemplate.update(sql, componenteId, pilarId, planoId, cicloId, entidadeId);
    }

    public int registrarConfigPlanosHistorico(Integer entidadeId, Integer cicloId, Integer pilarId, Integer componenteId, CurrentUser currentUser, String configuracaoAnterior, String motivacao) {
        String sql = "INSERT INTO virtus.produtos_componentes_historicos( " +
                " id_entidade, " +
                " id_ciclo, " +
                " id_pilar, " +
                " id_componente, " +
                " tipo_alteracao, " +
                " config, " +
                " config_anterior, " +
                " motivacao_config, " +
                " id_author, " +
                " criado_em, " +
                " id_versao_origem, " +
                " id_status) " +
                " SELECT " +
                " a.id_entidade, " +
                " a.id_ciclo, " +
                " a.id_pilar, " +
                " a.id_componente, " +
                " 'P', " +
                " COALESCE(cfg.planos_configurados, ''), " +
                " ?, " +  // configuracaoAnterior
                " ?, " +  // motivacao
                " ?, " +  // currentUser.getId()
                " GETDATE(), " +
                " a.id_author, " +
                " a.id_status " +
                " FROM virtus.produtos_componentes a " +
                " LEFT JOIN (SELECT pp.id_entidade, pp.id_ciclo, pp.id_pilar, pp.id_componente, " +
                " string_agg(pl.cnpb, ', ') planos_configurados " +
                " FROM virtus.produtos_planos pp " +
                " INNER JOIN virtus.planos pl ON pp.id_plano = pl.id_plano " +
                " GROUP BY pp.id_entidade, pp.id_ciclo, pp.id_pilar, pp.id_componente) cfg " +
                " ON (cfg.id_entidade = a.id_entidade AND cfg.id_pilar = a.id_pilar AND cfg.id_componente = a.id_componente) " +
                " WHERE a.id_entidade = ? " +  // entidadeId
                " AND a.id_ciclo = ? " +       // cicloId
                " AND a.id_pilar = ? " +       // pilarId
                " AND a.id_componente = ?";    // componenteId

        return jdbcTemplate.update(sql, configuracaoAnterior, motivacao, currentUser.getId(), entidadeId, cicloId, pilarId, componenteId);
    }


}
