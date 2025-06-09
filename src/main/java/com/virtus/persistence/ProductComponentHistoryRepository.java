package com.virtus.persistence;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.virtus.domain.dto.request.ProductComponentRequestDTO;
import com.virtus.domain.dto.response.ProductComponentHistoryDTO;
import com.virtus.domain.model.CurrentUser;

@Repository
public class ProductComponentHistoryRepository {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        Logger logger = LoggerFactory.getLogger(this.getClass());

        public List<ProductComponentHistoryDTO> find(Long entidadeId,
                        Long cicloId, Long pilarId, Long componenteId) {
                String sql = "SELECT  " +
                                "   a.id_produto_componente_historico,  " +
                                "   id_entidade,  " +
                                "   id_ciclo,  " +
                                "   id_pilar,  " +
                                "   id_componente,  " +
                                "   coalesce(format(inicia_em,'dd/MM/yyyy'),'') as inicia_em,  " +
                                "   coalesce(format(inicia_em_anterior,'dd/MM/yyyy'),'') as inicia_em_anterior,  " +
                                "   coalesce(format(termina_em,'dd/MM/yyyy'),'') as termina_em,  " +
                                "   coalesce(format(termina_em_anterior,'dd/MM/yyyy'),'') as termina_em_anterior,  " +
                                "   coalesce(config,'') as config,  " +
                                "   coalesce(config_anterior,'') as config_anterior,  " +
                                "   coalesce(peso,0) as peso,  " +
                                "   coalesce(id_tipo_pontuacao,0) as id_tipo_pontuacao,  " +
                                "   coalesce(nota,0) as nota,  " +
                                "   tipo_alteracao,  " +
                                "   coalesce(id_auditor,0) as id_auditor,  " +
                                "   coalesce(c.name,'') as auditor_name,  " +
                                "   coalesce(auditor_anterior_id,0) as auditor_anterior_id,  " +
                                "   coalesce(d.name,'') as auditor_anterior_name,  " +
                                "   a.id_author,  " +
                                "   coalesce(b.name,'') as author_name, " +
                                "   coalesce(format(a.criado_em, 'dd/MM/yyyy HH:mm:ss'),'') as alterado_em,  " +
                                "   case " +
                                "       when tipo_alteracao = 'R' then justificativa " +
                                "       when tipo_alteracao = 'I' then motivacao_reprogramacao " +
                                "       when tipo_alteracao = 'T' then motivacao_reprogramacao " +
                                "       when tipo_alteracao = 'P' then motivacao_config " +
                                "   end as motivacao " +
                                "FROM virtus.produtos_componentes_historicos a " +
                                "LEFT JOIN virtus.users b ON a.id_author = b.id_user " +
                                "LEFT JOIN virtus.users c ON a.id_auditor = c.id_user " +
                                "LEFT JOIN virtus.users d ON a.auditor_anterior_id = d.id_user " +
                                "WHERE a.id_entidade = ? AND " +
                                "   a.id_ciclo = ? AND " +
                                "   a.id_pilar = ? AND " +
                                "   a.id_componente = ? " +
                                "ORDER BY a.criado_em DESC";

                return jdbcTemplate.query(sql, new Object[] { entidadeId, cicloId, pilarId, componenteId },
                                (rs, rowNum) -> {
                                        ProductComponentHistoryDTO history = ProductComponentHistoryDTO.builder()
                                                        .idProdutoComponenteHistorico(
                                                                        rs.getLong("id_produto_componente_historico"))
                                                        .idEntidade(rs.getLong("id_entidade"))
                                                        .idCiclo(rs.getLong("id_ciclo"))
                                                        .idPilar(rs.getLong("id_pilar"))
                                                        .idComponente(rs.getLong("id_componente"))
                                                        .iniciaEm(rs.getString("inicia_em"))
                                                        .iniciaEmAnterior(rs.getString("inicia_em_anterior"))
                                                        .terminaEm(rs.getString("termina_em"))
                                                        .terminaEmAnterior(rs.getString("termina_em_anterior"))
                                                        .config(rs.getString("config"))
                                                        .configAnterior(rs.getString("config_anterior"))
                                                        .peso(rs.getDouble("peso"))
                                                        .idTipoPontuacao(rs.getInt("id_tipo_pontuacao"))
                                                        .nota(rs.getDouble("nota"))
                                                        .tipoAlteracao(rs.getString("tipo_alteracao"))
                                                        .idAuditor(rs.getLong("id_auditor"))
                                                        .auditorName(rs.getString("auditor_name"))
                                                        .auditorAnteriorId(rs.getLong("auditor_anterior_id"))
                                                        .auditorAnteriorName(rs.getString("auditor_anterior_name"))
                                                        .idAuthor(rs.getLong("id_author"))
                                                        .authorName(rs.getString("author_name"))
                                                        .alteradoEm(rs.getString("alterado_em"))
                                                        .motivacao(rs.getString("motivacao"))
                                                        .build();
                                        return history;
                                });
        }

        public void registerNewAuditorComponentHistory(CurrentUser currentUser, ProductComponentRequestDTO body) {
                String sql = "INSERT INTO virtus.produtos_componentes_historicos( " +
                                "id_entidade, id_ciclo, id_pilar, id_componente, id_tipo_pontuacao, peso, nota, tipo_alteracao, "
                                +
                                "justificativa, id_supervisor, id_auditor, auditor_anterior_id, id_author, criado_em, id_versao_origem, id_status) "
                                +
                                "SELECT " +
                                "id_entidade, id_ciclo, id_pilar, id_componente, id_tipo_pontuacao, peso, nota, 'R', " +
                                "justificativa, id_supervisor, id_auditor, ?, ?, GETDATE(), id_author, id_status " +
                                "FROM virtus.produtos_componentes " +
                                "WHERE id_entidade = ? AND id_ciclo = ? AND id_pilar = ? AND id_componente = ?";

                try {
                        int rows = jdbcTemplate.update(sql,
                                        body.getAuditorAnteriorId(),
                                        currentUser.getId(),
                                        body.getEntidadeId(),
                                        body.getCicloId(),
                                        body.getPilarId(),
                                        body.getComponenteId());

                        logger.info("Histórico de auditor registrado com sucesso. Linhas afetadas: {}", rows);
                } catch (DataAccessException ex) {
                        logger.error("Erro ao registrar histórico de auditor para entidade={}, ciclo={}, pilar={}, componente={}: {}",
                                        body.getEntidadeId(), body.getCicloId(), body.getPilarId(),
                                        body.getComponenteId(), ex.getMessage(), ex);
                        // opcional: lançar exceção customizada ou HTTP 500
                        throw new RuntimeException("Erro ao salvar histórico de auditor", ex);
                }
        }

}
