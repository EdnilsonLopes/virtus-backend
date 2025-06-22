package com.virtus.database.scripts;

public class HistoryTableScripts {
    public static String createProdutosPlanosHistoricosTable(String schema) {
        return String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects " +
                        "WHERE object_id = OBJECT_ID(N'%1$s.produtos_planos_historicos') AND type in (N'U')) " +
                        "BEGIN " +
                        "CREATE TABLE %1$s.produtos_planos_historicos (" +
                        "id_produto_plano_historico integer NOT NULL PRIMARY KEY, " +
                        "id_entidade integer, " +
                        "id_ciclo integer, " +
                        "id_pilar integer, " +
                        "id_plano integer, " +
                        "id_componente integer, " +
                        "id_tipo_pontuacao integer, " +
                        "peso double precision, " +
                        "nota double precision, " +
                        "tipo_alteracao char(2), " +
                        "motivacao_nota varchar(4000), " +
                        "justificativa varchar(4000), " +
                        "id_supervisor integer, " +
                        "id_auditor integer, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) " +
                        "END",
                schema);
    }

    public static String createProdutosCiclosHistoricosTable(String schema) {
        return String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects " +
                        "WHERE object_id = OBJECT_ID(N'%1$s.produtos_ciclos_historicos') AND type in (N'U')) " +
                        "BEGIN " +
                        "CREATE TABLE %1$s.produtos_ciclos_historicos ( " +
                        "id_produto_ciclo_historico integer NOT NULL PRIMARY KEY, " +
                        "id_entidade integer, " +
                        "id_ciclo integer, " +
                        "id_tipo_pontuacao integer, " +
                        "nota double precision, " +
                        "motivacao varchar(4000), " +
                        "id_supervisor integer, " +
                        "id_auditor integer, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) " +
                        "END",
                schema);
    }

    public static String createProdutosPilaresHistoricosTable(String schema) {
        return String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects " +
                        "WHERE object_id = OBJECT_ID(N'%1$s.produtos_pilares_historicos') AND type in (N'U')) " +
                        "BEGIN " +
                        "CREATE TABLE %1$s.produtos_pilares_historicos (" +
                        "id_produto_pilar_historico integer NOT NULL PRIMARY KEY, " +
                        "id_entidade integer, " +
                        "id_ciclo integer, " +
                        "id_pilar integer, " +
                        "id_tipo_pontuacao integer, " +
                        "peso double precision, " +
                        "nota double precision, " +
                        "tipo_alteracao char(1), " +
                        "motivacao_peso varchar(400), " +
                        "motivacao_nota varchar(4000), " +
                        "id_supervisor integer, " +
                        "id_auditor integer, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) " +
                        "END",
                schema);
    }

    public static String createProdutosComponentesHistoricosTable(String schema) {
        return String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects " +
                        "WHERE object_id = OBJECT_ID(N'%1$s.produtos_componentes_historicos') AND type in (N'U')) " +
                        "BEGIN " +
                        "CREATE TABLE %1$s.produtos_componentes_historicos (" +
                        "id_produto_componente_historico integer NOT NULL PRIMARY KEY, " +
                        "id_entidade integer, " +
                        "id_ciclo integer, " +
                        "id_pilar integer, " +
                        "id_plano integer, " +
                        "id_componente integer, " +
                        "id_tipo_pontuacao integer, " +
                        "peso double precision, " +
                        "nota double precision, " +
                        "config varchar(300), " +
                        "config_anterior varchar(300), " +
                        "tipo_alteracao char(1), " +
                        "motivacao_config varchar(4000), " +
                        "motivacao_reprogramacao varchar(4000), " +
                        "motivacao_peso varchar(4000), " +
                        "motivacao_nota varchar(4000), " +
                        "justificativa varchar(4000), " +
                        "id_supervisor integer, " +
                        "id_auditor integer, " +
                        "auditor_anterior_id integer, " +
                        "inicia_em datetime, " +
                        "termina_em datetime, " +
                        "inicia_em_anterior datetime, " +
                        "termina_em_anterior datetime, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) " +
                        "END",
                schema);
    }

    public static String createProdutosElementosHistoricosTable(String schema) {
        return String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects " +
                        "WHERE object_id = OBJECT_ID(N'%1$s.produtos_elementos_historicos') AND type in (N'U')) " +
                        "BEGIN " +
                        "CREATE TABLE %1$s.produtos_elementos_historicos (" +
                        "id_produto_elemento_historico integer NOT NULL PRIMARY KEY, " +
                        "id_entidade integer, " +
                        "id_ciclo integer, " +
                        "id_pilar integer, " +
                        "id_plano integer, " +
                        "id_componente integer, " +
                        "id_tipo_nota integer, " +
                        "id_elemento integer, " +
                        "id_tipo_pontuacao integer, " +
                        "peso double precision, " +
                        "nota double precision, " +
                        "tipo_alteracao char(1), " +
                        "motivacao_peso varchar(4000), " +
                        "motivacao_nota varchar(4000), " +
                        "justificativa varchar(4000), " +
                        "id_supervisor integer, " +
                        "id_auditor integer, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) " +
                        "END",
                schema);
    }

    public static String createProdutosItensHistoricosTable(String schema) {
        return String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects " +
                        "WHERE object_id = OBJECT_ID(N'%1$s.produtos_itens_historicos') AND type in (N'U')) " +
                        "BEGIN " +
                        "CREATE TABLE %1$s.produtos_itens_historicos (" +
                        "id_produto_item_historico integer NOT NULL PRIMARY KEY, " +
                        "id_entidade integer, " +
                        "id_ciclo integer, " +
                        "id_pilar integer, " +
                        "id_plano integer, " +
                        "id_componente integer, " +
                        "id_tipo_nota integer, " +
                        "id_elemento integer, " +
                        "id_item integer, " +
                        "avaliacao varchar(4000), " +
                        "anexo varchar(4000), " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) " +
                        "END",
                schema);
    }

    public static String createProdutosTiposNotasHistoricosTable(String schema) {
        return String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects " +
                        "WHERE object_id = OBJECT_ID(N'%1$s.produtos_tipos_notas_historicos') AND type in (N'U')) " +
                        "BEGIN " +
                        "CREATE TABLE %1$s.produtos_tipos_notas_historicos (" +
                        "id_produto_tipo_nota_historico integer NOT NULL PRIMARY KEY, " +
                        "id_entidade integer, " +
                        "id_ciclo integer, " +
                        "id_pilar integer, " +
                        "id_componente integer, " +
                        "id_tipo_nota integer, " +
                        "id_tipo_pontuacao integer, " +
                        "peso double precision, " +
                        "nota double precision, " +
                        "anexo varchar(4000), " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) " +
                        "END",
                schema);
    }

}
