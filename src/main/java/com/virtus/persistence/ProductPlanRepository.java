package com.virtus.persistence;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.virtus.domain.dto.response.ProductPlanResponseDTO;

@Repository
public class ProductPlanRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductPlanRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int inserirProdutosPlanos(Integer entidadeId, Integer cicloId, Integer pilarId, Integer componenteId,
            Integer planId, Integer tipoPontuacao, Integer authorId) {
        String sql = "INSERT INTO virtus.produtos_planos ( " +
                " id_entidade, " +
                " id_ciclo, " +
                " id_pilar, " +
                " id_componente, " +
                " id_plano, " +
                " nota, " +
                " id_tipo_pontuacao, " +
                " id_author, " +
                " criado_em ) " +
                " SELECT r.id_entidade, " +
                "    id_ciclo, " +
                "    id_pilar, " +
                "    r.id_componente, " +
                "    r.id_plano, " +
                "    CASE WHEN na.nota IS NULL THEN 0 ELSE na.nota END AS nota, " +
                "    id_tipo_pontuacao, " +
                "    r.id_author, " +
                "    r.criado_em " +
                " FROM " +
                "    (SELECT ce.id_entidade, " +
                "            ce.id_ciclo, " +
                "            a.id_pilar, " +
                "            b.id_componente, " +
                "            p.id_plano, " +
                "            MAX(na.id_nota_automatica) AS id_nota_automatica, " +
                "            ? AS id_tipo_pontuacao, " +
                "            ? AS id_author, " +
                "            GETDATE() AS criado_em " +
                "     FROM virtus.CICLOS_ENTIDADES ce " +
                "     INNER JOIN virtus.PILARES_CICLOS a ON ce.id_ciclo = a.id_ciclo " +
                "     INNER JOIN virtus.COMPONENTES_PILARES b ON a.id_pilar = b.id_pilar " +
                "     INNER JOIN virtus.PLANOS p ON p.id_entidade = ce.id_entidade " +
                "     LEFT JOIN virtus.notas_automaticas na ON na.cnpb = p.cnpb AND na.id_componente = b.id_componente "
                +
                "     WHERE a.id_ciclo = ce.id_ciclo " +
                "       AND ce.id_entidade = ? " +
                "       AND ce.id_ciclo = ? " +
                "       AND a.id_pilar = ? " +
                "       AND b.id_componente = ? " +
                "       AND p.id_plano = ? " +
                "     GROUP BY ce.id_entidade, ce.id_ciclo, a.id_pilar, b.id_componente, p.id_plano) AS R " +
                " INNER JOIN virtus.PLANOS p ON p.id_entidade = r.id_entidade AND p.id_plano = r.id_plano " +
                " LEFT JOIN virtus.notas_automaticas na ON na.cnpb = p.cnpb AND na.id_componente = r.id_componente AND na.id_nota_automatica = r.id_nota_automatica "
                +
                " WHERE NOT EXISTS " +
                "    (SELECT 1 FROM virtus.produtos_planos WHERE r.id_ciclo = id_ciclo AND r.id_entidade = id_entidade "
                +
                " AND r.id_pilar = id_pilar AND r.id_componente = id_componente AND r.id_plano = id_plano)";

        return jdbcTemplate.update(sql, tipoPontuacao, authorId, entidadeId, cicloId, pilarId, componenteId, planId);
    }

    public int deletarProdutosPlanos(Integer entidadeId, Integer cicloId, Integer pilarId, Integer componenteId) {
        String sql = "DELETE FROM virtus.produtos_planos " +
                "WHERE id_entidade = ? " +
                "AND id_ciclo = ? " +
                "AND id_pilar = ? " +
                "AND id_componente = ?";

        return jdbcTemplate.update(sql, entidadeId, cicloId, pilarId, componenteId);
    }

    public List<ProductPlanResponseDTO> listarConfigPlanos(Integer entidadeId, Integer cicloId, Integer pilarId,
            Integer componenteId) {
        String sql = "SELECT " +
                " a.id_produto_plano, " +
                " a.id_entidade, " +
                " a.id_plano " +
                " FROM virtus.produtos_planos a " +
                " WHERE a.id_entidade = ? " +
                " AND a.id_ciclo = ? " +
                " AND a.id_pilar = ? " +
                " AND a.id_componente = ?";

        return jdbcTemplate.query(sql,
                new Object[] { entidadeId, cicloId, pilarId, componenteId },
                (rs, rowNum) -> {
                    ProductPlanResponseDTO configPlano = new ProductPlanResponseDTO();
                    configPlano.setId(rs.getInt("id_produto_plano"));
                    configPlano.setEntityId(rs.getInt("id_entidade"));
                    configPlano.setPlanId(rs.getInt("id_plano"));
                    return configPlano;
                });
    }

    public String loadConfigPlanosConfigAnterior(Integer entidadeId, Integer cicloId, Integer pilarId,
            Integer componenteId) {
        String sql = "SELECT string_agg(b.cnpb, ', ') AS planos_configurados " +
                "FROM virtus.produtos_planos a " +
                "INNER JOIN virtus.planos b ON a.id_plano = b.id_plano " +
                "WHERE a.id_entidade = ? AND a.id_ciclo = ? " +
                "AND a.id_pilar = ? AND a.id_componente = ? " +
                "GROUP BY a.id_componente";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[] { entidadeId, cicloId, pilarId, componenteId },
                    String.class);
        } catch (EmptyResultDataAccessException e) {
            return "";
        }
    }

    public String findByCycleLevelIds(Long entidadeId, Long cicloId, Long pilarId, Long componenteId, Long planoId) {
        String sql = "SELECT analise FROM virtus.produtos_planos " +
                " WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? " +
                " AND id_componente = ? AND id_plano = ?";
        try {
            return jdbcTemplate.queryForObject(sql,
                    new Object[] { entidadeId,
                            cicloId,
                            pilarId,
                            componenteId,
                            planoId },
                    String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
