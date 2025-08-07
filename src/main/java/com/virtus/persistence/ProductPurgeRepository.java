package com.virtus.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductPurgeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductPurgeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int deleteProdutosItens(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_itens WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosElementos(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_elementos WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosTiposNotas(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_tipos_notas WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosPlanos(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_planos WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosComponentes(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_componentes WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosPilares(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_pilares WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosCiclos(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_ciclos WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteCiclosEntidades(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.ciclos_entidades WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosItensHistoricos(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_itens_historicos WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosElementosHistoricos(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_elementos_historicos WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosPlanosHistoricos(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_planos_historicos WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosComponentesHistoricos(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_componentes_historicos WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosPilaresHistoricos(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_pilares_historicos WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteProdutosCiclosHistoricos(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.produtos_ciclos_historicos WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

    public int deleteIntegrantes(Integer entityId, Integer cycleId) {
        String sql = "DELETE FROM virtus.integrantes WHERE id_entidade = ? AND id_ciclo = ?";
        return jdbcTemplate.update(sql, entityId, cycleId);
    }

}
