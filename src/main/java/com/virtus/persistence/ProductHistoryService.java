package com.virtus.persistence;

import com.virtus.domain.dto.request.ProductElementRequestDTO;
import com.virtus.domain.dto.response.HistoryComponentDTO;
import com.virtus.domain.model.CurrentUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductHistoryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<HistoryComponentDTO> findComponentHistoryByEntityAndCycleAndPillarAndComponent(Long entidadeId, Long cicloId, Long pilarId, Long componenteId) {
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
                "   coalesce(auditor_anterior_id,0) as auditor_anterior_id,  " +
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
                "WHERE a.id_entidade = ? AND " +
                "   a.id_ciclo = ? AND " +
                "   a.id_pilar = ? AND " +
                "   a.id_componente = ? " +
                "ORDER BY a.criado_em DESC";

        return jdbcTemplate.query(sql, new Object[]{entidadeId, cicloId, pilarId, componenteId},
                (rs, rowNum) -> {
                    HistoryComponentDTO history = HistoryComponentDTO.builder()
                            .idProdutoComponenteHistorico(rs.getLong("id_produto_componente_historico"))
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
                            .auditorAnteriorId(rs.getLong("auditor_anterior_id"))
                            .idAuthor(rs.getLong("id_author"))
                            .authorName(rs.getString("author_name"))
                            .alteradoEm(rs.getString("alterado_em"))
                            .motivacao(rs.getString("motivacao"))
                            .build();

                    return history;
                });
    }

    public void updateElementGradeHistory(ProductElementRequestDTO dto, CurrentUser currentUser) {
        String sql = 
            "INSERT INTO virtus.produtos_elementos_historicos ( " +
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
            dto.getElementoId()                      
        );
    }

 public void updateElementWeightHistory(ProductElementRequestDTO dto, CurrentUser currentUser) {
        String sql =
            "INSERT INTO virtus.produtos_elementos_historicos ( " +
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
            dto.getElementoId()                     
        );
    }
}

