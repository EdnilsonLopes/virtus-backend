package com.virtus.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void inserirProdutosItens(Integer cicloId, Integer entidadeId, Integer pilarId, Integer componenteId, Integer planoId, Integer authorId) {
        String sql = "INSERT INTO virtus.produtos_itens ( " +
                " id_entidade, " +
                " id_ciclo, " +
                " id_pilar, " +
                " id_componente, " +
                " id_plano, " +
                " id_tipo_nota, " +
                " id_elemento, " +
                " id_item, " +
                " id_author, " +
                " criado_em ) " +
                " SELECT " +
                " p.id_entidade, " +
                " p.id_ciclo, " +
                " p.id_pilar, " +
                " p.id_componente, " +
                " p.id_plano, " +
                " c.id_tipo_nota, " +
                " c.id_elemento, d.id_item, ?, GETDATE() " +
                " FROM virtus.pilares_ciclos a " +
                " INNER JOIN virtus.componentes_pilares b ON a.id_pilar = b.id_pilar " +
                " INNER JOIN virtus.elementos_componentes c ON b.id_componente = c.id_componente " +
                " INNER JOIN virtus.itens d ON c.id_elemento = d.id_elemento " +
                " INNER JOIN virtus.produtos_planos p ON " +
                " ( b.id_componente = p.id_componente AND a.id_pilar = p.id_pilar AND a.id_ciclo = p.id_ciclo ) " +
                " WHERE " +
                " a.id_ciclo = ? " +
                " AND p.id_entidade = ? " +
                " AND p.id_pilar = ? " +
                " AND p.id_componente = ? " +
                " AND p.id_plano = ? " +
                " AND NOT EXISTS (SELECT 1 " +
                "     FROM virtus.produtos_itens e " +
                "     WHERE e.id_entidade = ? " +
                "       AND e.id_plano = ? " +
                "       AND e.id_ciclo = p.id_ciclo " +
                "       AND e.id_pilar = p.id_pilar " +
                "       AND e.id_componente = b.id_componente " +
                "       AND e.id_elemento = c.id_elemento " +
                "       AND e.id_item = d.id_item)";

        jdbcTemplate.update(sql, authorId, cicloId, entidadeId, pilarId, componenteId, planoId, entidadeId, planoId);
    }

}
