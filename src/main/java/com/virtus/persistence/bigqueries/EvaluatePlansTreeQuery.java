package com.virtus.persistence.bigqueries;

public class EvaluatePlansTreeQuery {

    public static final String EVALUATE_PLANS_TREE_QUERY = "SELECT a.id_entidade, " +
            " coalesce(b.nome, '') AS entidade_nome, " +
            " a.id_ciclo, " +
            " coalesce(c.nome, '') AS ciclo_nome, " +
            " coalesce(pdc.nota, 0) AS ciclo_nota, " +
            " a.id_pilar, " +
            " coalesce(d.nome, '') AS pilar_nome, " +
            " coalesce(f.peso, 0) AS pilar_peso, " +
            " coalesce(f.nota, 0) AS pilar_nota, " +
            " a.id_componente, " +
            " coalesce(e.nome, '') AS componente_nome, " +
            " round(coalesce(g.peso, 0), 2) AS componente_peso, " +
            " coalesce(g.nota, 0) AS componente_nota, " +
            " coalesce(g.id_supervisor, 0) AS super_id, " +
            " coalesce(h.name, '') AS supervisor_nome, " +
            " coalesce(g.id_auditor, 0) AS auditor_id, " +
            " coalesce(o.id_tipo_nota,0) AS id_tipo_nota, " +
            " coalesce(m.letra,'') AS letra, " +
            " coalesce(m.cor_letra,'') AS cor_letra, " +
            " coalesce(m.nome,'') AS nome, " +
            " coalesce(o.peso, 0) AS tipo_nota_peso, " +
            " coalesce(o.nota, 0) AS tipo_nota_nota, " +
            " coalesce(n.id_elemento, 0) AS id_elemento, " +
            " coalesce(k.nome, '') AS elemento_nome, " +
            " coalesce(n.peso, 0) AS elemento_peso, " +
            " coalesce(n.nota, 0) AS elemento_nota, " +
            " coalesce(n.id_tipo_pontuacao, 0) AS id_tipo_pontuacao, " +
            " coalesce(ec.peso_padrao, 0) AS peso_padrao, " +
            " coalesce(cp.tipo_media, 1) AS tipo_media, " +
            " cp.peso_padrao AS peso_padrao_cp, " +
            " coalesce(pc.tipo_media, 1) AS tipo_media_pc, " +
            " pc.peso_padrao as peso_padrao_pc, " +
            " coalesce(i.id_item, 0) AS id_item, " +
            " coalesce(l.nome, '') AS item_nome, " +
            " a.id_plano, " +
            " j.cnpb, " +
            " CASE " +
            " 	WHEN j.recurso_garantidor >= 1000000 " +
            " AND j.recurso_garantidor < 1000000000 THEN concat(format(j.recurso_garantidor/1000000, 'N', 'pt-br'), ' Milhões') " +
            " WHEN j.recurso_garantidor >= 1000000000 THEN concat(format(j.recurso_garantidor/1000000000, 'N', 'pt-br'), ' Bilhões') " +
            " ELSE concat(format(j.recurso_garantidor/1000, 'N', 'pt-br'), ' Milhares') " +
            " END, " +
            " j.id_modalidade, " +
            " round(coalesce(a.peso, 0), 2) AS plano_peso, " +
            " round(coalesce(a.nota, 0), 2) AS plano_nota, " +
            " coalesce(format(g.inicia_em, 'dd/MM/yyyy'), '') AS inicia_em, " +
            " coalesce(format(g.termina_em, 'dd/MM/yyyy'), '') AS termina_em, " +
            " coalesce(sta.name, '') AS cstatus, " +
            " coalesce(g.id_status, '0') AS id_status, " +
            " g.id_produto_componente, " +
            " CASE " +
            " WHEN g.inicia_em IS NOT NULL " +
            " AND g.termina_em IS NOT NULL " +
            " AND GETDATE() BETWEEN coalesce(g.inicia_em, CAST('0001-01-01' AS DATE)) AND coalesce(dateadd(DAY, 1, g.termina_em), CAST('9999-12-31' AS DATE)) THEN 1 " +
            " ELSE 0 " +
            " END AS periodo_permitido, " +
            " CASE " +
            " WHEN ce.inicia_em IS NOT NULL " +
            " AND ce.termina_em IS NOT NULL " +
            " AND GETDATE() BETWEEN coalesce(ce.inicia_em, CAST('0001-01-01' AS DATE)) AND coalesce(dateadd(DAY, 1, ce.termina_em), CAST('9999-12-31' AS DATE)) THEN 1 " +
            " ELSE 0 " +
            " END AS periodo_ciclo " +
            " FROM virtus.produtos_planos a " +
            " INNER JOIN virtus.entidades b ON a.id_entidade = b.id_entidade " +
            " INNER JOIN virtus.ciclos c ON a.id_ciclo = c.id_ciclo " +
            " INNER JOIN virtus.produtos_ciclos pdc ON (a.id_ciclo = pdc.id_ciclo " +
            " AND a.id_entidade = pdc.id_entidade) " +
            " INNER JOIN virtus.pilares d ON a.id_pilar = d.id_pilar " +
            " INNER JOIN virtus.componentes e ON a.id_componente = e.id_componente " +
            " INNER JOIN virtus.produtos_pilares f ON (a.id_pilar = f.id_pilar " +
            " AND a.id_ciclo = f.id_ciclo " +
            " AND a.id_entidade = f.id_entidade) " +
            " INNER JOIN virtus.produtos_componentes g ON (a.id_componente = g.id_componente " +
            " AND a.id_pilar = g.id_pilar " +
            " AND a.id_ciclo = g.id_ciclo " +
            " AND a.id_entidade = g.id_entidade) " +
            " LEFT JOIN virtus.users h ON g.id_supervisor = h.id_user " +
            " LEFT JOIN virtus.produtos_tipos_notas o ON (a.id_plano = o.id_plano " +
            " AND a.id_componente = o.id_componente " +
            " AND a.id_pilar = o.id_pilar " +
            " AND a.id_ciclo = o.id_ciclo " +
            " AND a.id_entidade = o.id_entidade) " +
            " LEFT JOIN virtus.tipos_notas m ON o.id_tipo_nota = m.id_tipo_nota " +
            " LEFT JOIN virtus.produtos_elementos n ON (o.id_tipo_nota = n.id_tipo_nota " +
            " AND a.id_plano = n.id_plano " +
            " AND a.id_componente = n.id_componente " +
            " AND a.id_pilar = n.id_pilar " +
            " AND a.id_ciclo = n.id_ciclo " +
            " AND a.id_entidade = n.id_entidade) " +
            " LEFT JOIN virtus.elementos k ON n.id_elemento = k.id_elemento " +
            " LEFT JOIN virtus.elementos_componentes ec ON (n.id_elemento = ec.id_elemento " +
            " AND o.id_tipo_nota = ec.id_tipo_nota " +
            " AND a.id_componente = ec.id_componente) " +
            " LEFT JOIN virtus.componentes_pilares cp ON (a.id_componente = cp.id_componente " +
            " AND a.id_pilar = cp.id_pilar) " +
            " LEFT JOIN virtus.pilares_ciclos pc ON (a.id_pilar = pc.id_pilar " +
            " AND a.id_ciclo = pc.id_ciclo) " +
            " LEFT JOIN virtus.produtos_itens i ON ( " +
            " n.id_elemento = i.id_elemento " +
            " AND o.id_tipo_nota = i.id_tipo_nota " +
            " AND o.id_plano = i.id_plano " +
            " AND o.id_componente = i.id_componente " +
            " AND o.id_pilar = i.id_pilar " +
            " AND o.id_ciclo = i.id_ciclo " +
            " AND o.id_entidade = i.id_entidade) " +
            " LEFT JOIN virtus.itens l ON i.id_item = l.id_item " +
            " LEFT JOIN virtus.planos j ON a.id_plano = j.id_plano " +
            " LEFT JOIN virtus.ciclos_entidades ce ON (ce.id_ciclo = c.id_ciclo AND ce.id_entidade = b.id_entidade) " +
            " LEFT JOIN virtus.status sta ON g.id_status = sta.id_status " +
            " WHERE a.id_entidade = ? " +
            "   AND a.id_ciclo = ? " +
            " ORDER BY a.id_ciclo, " +
            "          a.id_pilar, " +
            "          a.id_componente, " +
            "          j.recurso_garantidor DESC, " +
            "          o.id_tipo_nota, " +
            "          l.id_elemento, " +
            "          l.id_item ";

}
