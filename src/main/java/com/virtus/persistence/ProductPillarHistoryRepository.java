package com.virtus.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.virtus.domain.dto.request.ProductPillarRequestDTO;
import com.virtus.domain.dto.response.ProductPillarHistoryDTO;
import com.virtus.domain.model.CurrentUser;

@Repository
public class ProductPillarHistoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void record(ProductPillarRequestDTO dto, CurrentUser currentUser, String changeType) {
        String sql = "INSERT INTO virtus.produtos_pilares_historicos ( " +
                "    id_entidade, " +
                "    id_ciclo, " +
                "    id_pilar, " +
                "    id_tipo_pontuacao, " +
                "    peso, " +
                "    nota, " +
                "    tipo_alteracao, " +
                "    motivacao_peso, " +
                "    id_supervisor, " +
                "    id_auditor, " +
                "    id_author, " +
                "    criado_em, " +
                "    id_versao_origem, " +
                "    id_status " +
                ") " +
                "SELECT " +
                "    id_entidade, " +
                "    id_ciclo, " +
                "    id_pilar, " +
                "    1 as id_tipo_pontuacao, " +
                "    peso, " +
                "    nota, " +
                "    ?, " +
                "    motivacao_peso, " +
                "    ?, " +
                "    id_auditor, " +
                "    ?, " +
                "    CURRENT_TIMESTAMP, " +
                "    id_author, " +
                "    id_status " +
                "FROM virtus.produtos_pilares " +
                "WHERE id_entidade = ? " +
                "  AND id_ciclo = ? " +
                "  AND id_pilar = ?";

        jdbcTemplate.update(sql,
                changeType,
                dto.getSupervisorId(),
                currentUser.getId(),
                dto.getEntidadeId(),
                dto.getCicloId(),
                dto.getPilarId());
    }

    public List<ProductPillarHistoryDTO> find(Long entidadeId, Long cicloId, Long pilarId) {
        String sql = "SELECT " +
                "  a.id_produto_pilar_historico, " +
                "  a.id_entidade, " +
                "  a.id_ciclo, " +
                "  a.id_pilar, " +
                "  COALESCE(a.peso, 0) AS peso, " +
                "  COALESCE(LAG(a.peso) OVER (PARTITION BY a.id_entidade, a.id_ciclo, a.id_pilar ORDER BY a.criado_em), 0) AS peso_anterior, "
                +
                "  a.id_tipo_pontuacao, " +
                "  COALESCE(a.nota, 0) AS nota, " +
                "  COALESCE(LAG(a.nota) OVER (PARTITION BY a.id_entidade, a.id_ciclo, a.id_pilar ORDER BY a.criado_em), 0) AS nota_anterior, "
                +
                "  a.tipo_alteracao, " +
                "  a.id_author, " +
                "  COALESCE(b.name, '') AS author_name, " +
                "  COALESCE(FORMAT(a.criado_em, 'dd/MM/yyyy HH:mm:ss'), '') AS alterado_em, " +
                "  a.motivacao_peso " +
                "FROM virtus.produtos_pilares_historicos a " +
                "LEFT JOIN virtus.users b ON a.id_author = b.id_user " +
                "WHERE a.id_entidade = ? " +
                "  AND a.id_ciclo = ? " +
                "  AND a.id_pilar = ? " +
                "ORDER BY a.criado_em DESC";
        return jdbcTemplate.query(sql, new Object[] { entidadeId, cicloId, pilarId },
                (rs, rowNum) -> {
                    ProductPillarHistoryDTO dto = new ProductPillarHistoryDTO();
                    dto.setIdProdutoPilarHistorico(rs.getLong("id_produto_pilar_historico"));
                    dto.setIdEntidade(rs.getLong("id_entidade"));
                    dto.setIdCiclo(rs.getLong("id_ciclo"));
                    dto.setIdPilar(rs.getLong("id_pilar"));
                    dto.setPeso(rs.getDouble("peso"));
                    dto.setPesoAnterior(rs.getDouble("peso_anterior"));
                    dto.setNota(rs.getDouble("nota"));
                    dto.setNotaAnterior(rs.getDouble("nota_anterior"));
                    int tipo = rs.getInt("id_tipo_pontuacao");
                    String tipoStr;
                    switch (tipo) {
                        case 1:
                            tipoStr = "Manual";
                            break;
                        case 2:
                            tipoStr = "Automático";
                            break;
                        default:
                            tipoStr = "Desconhecido"; // ou null, dependendo do seu caso
                    }
                    dto.setMetodo(tipoStr);
                    dto.setTipoAlteracao(rs.getString("tipo_alteracao"));
                    dto.setIdAuthor(rs.getLong("id_author"));
                    dto.setAuthorName(rs.getString("author_name"));
                    dto.setAlteradoEm(rs.getString("alterado_em"));
                    dto.setMotivacaoPeso(rs.getString("motivacao_peso"));
                    return dto;
                });
    }
}
