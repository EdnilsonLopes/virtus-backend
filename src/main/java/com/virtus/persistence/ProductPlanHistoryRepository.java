package com.virtus.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.virtus.domain.dto.response.HistoricalSeriesDTO;
import com.virtus.domain.dto.response.ProductPlanHistoryDTO;

@Repository
public class ProductPlanHistoryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProductPlanHistoryDTO> find(Long entidadeId, Long cicloId, Long pilarId, Long componenteId,
            Long planoId) {
        String sql = "SELECT " +
                "a.id_produto_plano_historico, " +
                "a.id_entidade, " +
                "a.id_ciclo, " +
                "a.id_pilar, " +
                "a.id_componente, " +
                "a.id_plano, " +
                "a.id_tipo_pontuacao, " +
                "COALESCE(a.nota, 0) AS nota, " +
                "COALESCE(LAG(a.nota) OVER (PARTITION BY a.id_entidade, a.id_ciclo, a.id_pilar, a.id_componente, a.id_plano ORDER BY a.criado_em), 0) AS nota_anterior, "
                +
                "a.tipo_alteracao, " +
                "a.id_author, " +
                "COALESCE(b.name, '') AS author_name, " +
                "COALESCE(FORMAT(a.criado_em, 'dd/MM/yyyy HH:mm:ss'), '') AS alterado_em, " +
                "a.motivacao_nota as motivacao " +
                "FROM virtus.produtos_planos_historicos a " +
                "LEFT JOIN virtus.users b ON a.id_author = b.id_user " +
                "WHERE a.id_entidade = ? " +
                "AND a.id_ciclo = ? " +
                "AND a.id_pilar = ? " +
                "AND a.id_componente = ? " +
                "AND a.id_plano = ? " +
                "ORDER BY a.criado_em DESC";

        // Executa a consulta SQL e mapeia o resultado para uma lista de
        // ProductPlanHistoryDTO
        return jdbcTemplate.query(sql, new Object[] { entidadeId, cicloId, pilarId, componenteId, planoId },
                (rs, rowNum) -> {
                    ProductPlanHistoryDTO dto = new ProductPlanHistoryDTO();
                    dto.setIdProdutoPlanoHistorico(rs.getLong("id_produto_plano_historico"));
                    dto.setIdEntidade(rs.getLong("id_entidade"));
                    dto.setIdCiclo(rs.getLong("id_ciclo"));
                    dto.setIdPilar(rs.getLong("id_pilar"));
                    dto.setIdComponente(rs.getLong("id_componente"));
                    dto.setIdPlano(rs.getLong("id_plano"));
                    dto.setNota(rs.getDouble("nota"));
                    dto.setNotaAnterior(rs.getDouble("nota_anterior"));
                    dto.setTipoAlteracao(rs.getString("tipo_alteracao"));
                    dto.setIdAuthor(rs.getLong("id_author"));
                    dto.setAuthorName(rs.getString("author_name"));
                    dto.setAlteradoEm(rs.getString("alterado_em"));
                    dto.setMotivacao(rs.getString("motivacao"));
                    return dto;
                });
    }

    public List<HistoricalSeriesDTO> findHistoricalSeries(Long entidadeId, Long cicloId, Long pilarId,
            Long componenteId, Long planoId) {
        String sql = "SELECT " +
                "COALESCE(na.nota, 0) AS nota, " +
                "COALESCE(na.data_referencia, '') AS dataReferencia, " +
                "COALESCE(FORMAT(na.criado_em, 'dd/MM/yyyy HH:mm:ss'), '') AS criadoEm " +
                "FROM virtus.notas_automaticas na JOIN virtus.planos p ON na.cnpb = p.cnpb " +
                "WHERE na.id_componente = ? " +
                "AND p.id_plano = ? " +
                "ORDER BY data_referencia DESC";

        // Executa a consulta SQL e mapeia o resultado para uma lista de
        // HistoricalSeriesDTO
        return jdbcTemplate.query(sql, new Object[] { componenteId, planoId },
                (rs, rowNum) -> {
                    HistoricalSeriesDTO dto = new HistoricalSeriesDTO();
                    dto.setNota(rs.getDouble("nota"));
                    dto.setDataReferencia(rs.getString("dataReferencia"));
                    dto.setCriadoEm(rs.getString("criadoEm"));
                    return dto;
                });
    }
}
