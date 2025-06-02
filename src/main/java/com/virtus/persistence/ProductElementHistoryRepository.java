package com.virtus.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.virtus.domain.dto.request.ProductElementRequestDTO;
import com.virtus.domain.dto.response.ProductElementHistoryDTO;
import com.virtus.domain.model.CurrentUser;

@Repository
public class ProductElementHistoryRepository {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        public void recordElementGradeHistory(ProductElementRequestDTO dto, CurrentUser currentUser) {
                String sql = "INSERT INTO virtus.produtos_elementos_historicos ( " +
                                "   id_entidade, id_ciclo, id_pilar, id_plano, id_componente, " +
                                "   id_tipo_nota, id_elemento, id_tipo_pontuacao, peso, nota, tipo_alteracao, " +
                                "   motivacao_nota, id_supervisor, id_auditor, id_author, criado_em, " +
                                "   id_versao_origem, id_status) " +
                                "SELECT " +
                                "   id_entidade, id_ciclo, id_pilar, id_plano, id_componente, " +
                                "   id_tipo_nota, id_elemento, id_tipo_pontuacao, peso, nota, 'N', " +
                                "   motivacao_nota, id_supervisor, id_auditor, ?, GETDATE(), " +
                                "   id_author, id_status " +
                                "FROM virtus.produtos_elementos " +
                                "WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? AND " +
                                "      id_plano = ? AND id_componente = ? AND id_tipo_nota = ? AND id_elemento = ?";

                jdbcTemplate.update(
                                sql,
                                currentUser.getId(),
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getPlanoId(),
                                dto.getComponenteId(),
                                dto.getTipoNotaId(),
                                dto.getElementoId());
        }

        public void recordElementWeightHistory(ProductElementRequestDTO dto, CurrentUser currentUser) {
                String sql = "INSERT INTO virtus.produtos_elementos_historicos ( " +
                                "   id_entidade, id_ciclo, id_pilar, id_plano, id_componente, " +
                                "   id_tipo_nota, id_elemento, id_tipo_pontuacao, peso, nota, tipo_alteracao, " +
                                "   motivacao_peso, id_supervisor, id_auditor, id_author, criado_em, " +
                                "   id_versao_origem, id_status) " +
                                "SELECT " +
                                "   id_entidade, id_ciclo, id_pilar, id_plano, id_componente, " +
                                "   id_tipo_nota, id_elemento, id_tipo_pontuacao, peso, nota, 'P', " +
                                "   motivacao_peso, id_supervisor, id_auditor, ?, GETDATE(), " +
                                "   id_author, id_status " +
                                "FROM virtus.produtos_elementos " +
                                "WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? AND " +
                                "      id_componente = ? AND id_tipo_nota = ? AND id_elemento = ?";

                jdbcTemplate.update(
                                sql,
                                currentUser.getId(),
                                dto.getEntidadeId(),
                                dto.getCicloId(),
                                dto.getPilarId(),
                                dto.getComponenteId(),
                                dto.getTipoNotaId(),
                                dto.getElementoId());
        }

        public List<ProductElementHistoryDTO> find(Long entidadeId, Long cicloId, Long pilarId, Long componenteId,
                        Long planoId, Long elementoId) {
                String sql = "SELECT " +
                                " a.id_produto_elemento_historico, " +
                                " a.id_entidade, " +
                                " a.id_ciclo, " +
                                " a.id_pilar, " +
                                " a.id_plano, " +
                                " a.id_componente, " +
                                " a.id_tipo_nota, " +
                                " a.id_elemento, " +
                                " a.id_tipo_pontuacao, " +
                                " a.peso, " +
                                " a.nota, " +
                                " a.id_author, " +
                                " COALESCE(b.name, ''), " +
                                " COALESCE(FORMAT(a.criado_em, 'dd/MM/yyyy HH:mm:ss'), '') AS alterado_em, " +
                                " CASE WHEN tipo_alteracao = 'P' THEN a.motivacao_peso ELSE a.motivacao_nota END AS motivacao, "
                                +
                                " CASE WHEN tipo_alteracao = 'P' THEN 'Peso' ELSE 'Nota' END AS tipo_alteracao " +
                                "FROM virtus.produtos_elementos_historicos a " +
                                "LEFT JOIN virtus.users b ON a.id_author = b.id_user " +
                                "WHERE a.id_entidade = ? " +
                                "AND a.id_ciclo = ? " +
                                "AND a.id_pilar = ? " +
                                "AND a.id_plano = ? " +
                                "AND a.id_componente = ? " +
                                "AND a.id_elemento = ? " +
                                "ORDER BY a.criado_em DESC";

                return jdbcTemplate.query(sql, new Object[] {
                                entidadeId, cicloId, pilarId, planoId, componenteId, elementoId
                }, (rs, rowNum) -> {
                        ProductElementHistoryDTO dto = new ProductElementHistoryDTO();
                        dto.setIdProdutoElementoHistorico(rs.getLong(1));
                        dto.setIdEntidade(rs.getLong(2));
                        dto.setIdCiclo(rs.getLong(3));
                        dto.setIdPilar(rs.getLong(4));
                        dto.setIdPlano(rs.getLong(5));
                        dto.setIdComponente(rs.getLong(6));
                        dto.setIdTipoNota(rs.getLong(7));
                        dto.setIdElemento(rs.getLong(8));
                        int metodoId = rs.getInt(9);
                        dto.setIdTipoPontuacao(metodoId);
                        dto.setPeso(rs.getDouble(10));
                        dto.setNota(rs.getDouble(11));
                        dto.setIdAuthor(rs.getLong(12));
                        dto.setAuthorName(rs.getString(13));
                        dto.setAlteradoEm(rs.getString(14));
                        dto.setMotivacao(rs.getString(15));
                        dto.setTipoAlteracao(rs.getString(16));

                        switch (metodoId) {
                                case 1:
                                        dto.setMetodo("Manual");
                                        break;
                                case 2:
                                        dto.setMetodo("Calculada");
                                        break;
                                case 3:
                                        dto.setMetodo("Ajustada");
                                        break;
                                default:
                                        dto.setMetodo("Desconhecido");
                                        break;
                        }
                        return dto;
                });
        }

}
