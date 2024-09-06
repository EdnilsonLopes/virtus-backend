package com.virtus.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductElementRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductElementRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int inserirProdutosElementos(Integer entidadeId, Integer cicloId, Integer pilarId, Integer componenteId, Integer planoId, Integer tipoPontuacao, Integer authorId) {
        String sql = "INSERT INTO virtus.produtos_elementos ( " +
                " id_entidade, " +
                " id_ciclo, " +
                " id_pilar, " +
                " id_componente, " +
                " id_plano, " +
                " id_tipo_nota, " +
                " id_elemento, " +
                " peso, " +
                " nota, " +
                " id_tipo_pontuacao, " +
                " id_author, " +
                " criado_em ) " +
                " SELECT " +
                " d.id_entidade, " +
                " d.id_ciclo, " +
                " d.id_pilar, " +
                " d.id_componente, " +
                " d.id_plano, " +
                " c.id_tipo_nota, " +
                " c.id_elemento, " +
                " c.peso_padrao, " +
                " 0, ?, ?, GETDATE() " +
                " FROM virtus.pilares_ciclos a " +
                " INNER JOIN virtus.componentes_pilares b ON a.id_pilar = b.id_pilar " +
                " INNER JOIN virtus.elementos_componentes c ON b.id_componente = c.id_componente " +
                " INNER JOIN virtus.produtos_planos d ON (b.id_componente = d.id_componente AND a.id_pilar = b.id_pilar AND a.id_ciclo = d.id_ciclo) " +
                " WHERE a.id_ciclo = ? " +
                "   AND d.id_entidade = ? " +
                "   AND d.id_pilar = ? " +
                "   AND d.id_componente = ? " +
                "   AND d.id_plano = ? " +
                "   AND NOT EXISTS " +
                "     (SELECT 1 FROM virtus.produtos_elementos e " +
                "      WHERE e.id_entidade = ? " +
                "        AND e.id_ciclo = a.id_ciclo " +
                "        AND e.id_pilar = a.id_pilar " +
                "        AND e.id_componente = b.id_componente " +
                "        AND e.id_plano = ? " +
                "        AND e.id_elemento = c.id_elemento)";

        return jdbcTemplate.update(sql, tipoPontuacao, authorId, cicloId, entidadeId, pilarId, componenteId, planoId, entidadeId, planoId);
    }

}
