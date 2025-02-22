package com.virtus.persistence;

import com.virtus.domain.dto.response.EvaluatePlansTreeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.virtus.persistence.bigqueries.EvaluatePlansTreeQuery.EVALUATE_PLANS_TREE_QUERY;

@Repository
public class EvaluatePlansRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<EvaluatePlansTreeResponseDTO> findPlansByEntityAndCycle(Integer entidadeId, Integer cicloId) {
        String sql = EVALUATE_PLANS_TREE_QUERY;

        return jdbcTemplate.query(sql, new Object[]{entidadeId, cicloId},
                (rs, rowNum) -> {
                    EvaluatePlansTreeResponseDTO evaluatePlan = EvaluatePlansTreeResponseDTO.builder()
                            .entidadeId(rs.getInt("id_entidade"))
                            .entidadeNome(rs.getString("entidade_nome"))
                            .cicloId(rs.getInt("id_ciclo"))
                            .cicloNome(rs.getString("ciclo_nome"))
                            .cicloNota(rs.getBigDecimal("ciclo_nota"))
                            .pilarId(rs.getInt("id_pilar"))
                            .pilarNome(rs.getString("pilar_nome"))
                            .pilarPeso(rs.getBigDecimal("pilar_peso"))
                            .pilarNota(rs.getBigDecimal("pilar_nota"))
                            .componenteId(rs.getInt("id_componente"))
                            .componenteNome(rs.getString("componente_nome"))
                            .componentePeso(rs.getBigDecimal("componente_peso"))
                            .componenteNota(rs.getBigDecimal("componente_nota"))
                            .supervisorId(rs.getInt("super_id"))
                            .supervisorNome(rs.getString("supervisor_nome"))
                            .auditorId(rs.getInt("auditor_id"))
                            .tipoNotaId(rs.getInt("id_tipo_nota"))
                            .tipoNotaLetra(rs.getString("letra"))
                            .tipoNotaCorLetra(rs.getString("cor_letra"))
                            .tipoNotaNome(rs.getString("nome"))
                            .tipoNotaPeso(rs.getBigDecimal("tipo_nota_peso"))
                            .tipoNotaNota(rs.getBigDecimal("tipo_nota_nota"))
                            .elementoId(rs.getInt("id_elemento"))
                            .elementoNome(rs.getString("elemento_nome"))
                            .elementoPeso(rs.getBigDecimal("elemento_peso"))
                            .elementoNota(rs.getBigDecimal("elemento_nota"))
                            .tipoPontuacaoId(rs.getInt("id_tipo_pontuacao"))
                            .pesoPadraoEC(rs.getBigDecimal("peso_padrao"))
                            .tipoMediaCPId(rs.getInt("tipo_media"))
                            .pesoPadraoCP(rs.getBigDecimal("peso_padrao_cp"))
                            .tipoMediaPCId(rs.getInt("tipo_media_pc"))
                            .pesoPadraoPC(rs.getBigDecimal("peso_padrao_pc"))
                            .itemId(rs.getInt("id_item"))
                            .itemNome(rs.getString("item_nome"))
                            .planoId(rs.getInt("id_plano"))
                            .cnpb(rs.getString("cnpb"))
                            //.recursoGarantidor(rs.getString("recurso_garantidor"))
                            //.planoModalidade(rs.getString("plano_modalidade"))
                            .planoPeso(rs.getBigDecimal("plano_peso"))
                            .planoNota(rs.getBigDecimal("plano_nota"))
                            //.iniciaEm(rs.getString("inicia_em"))
                            //.terminaEm(rs.getString("termina_em"))
                            .cStatus(rs.getString("cstatus"))
                            .statusId(rs.getInt("id_status"))
                            .produtoComponenteId(rs.getInt("id_produto_componente"))
                            .periodoPermitido(rs.getBoolean("periodo_permitido"))
                            .periodoCiclo(rs.getBoolean("periodo_ciclo"))
                            .build();

                    return evaluatePlan;
                });
    }
}

