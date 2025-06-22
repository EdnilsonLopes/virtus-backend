package com.virtus.database.scripts;

public class ProductTableScripts {
        // Table PRODUTOS_CICLOS
        public static String createProdutosCiclosTable(String schema) {
                String sql = String
                                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.produtos_ciclos') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.produtos_ciclos (" +
                                                "id_produto_ciclo integer NOT NULL PRIMARY KEY, " +
                                                "id_entidade integer, id_ciclo integer, id_tipo_pontuacao integer, " +
                                                "nota double precision, analise varchar(MAX), motivacao varchar(MAX), "
                                                +
                                                "id_supervisor integer, id_auditor integer, id_author integer, " +
                                                "criado_em datetime, id_versao_origem integer, id_status integer) END ",
                                                schema, schema);
                return sql;
        }

        // Table PRODUTOS_PILARES
        public static String createProdutosPilaresTable(String schema) {
                String sql = String.format(
                                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.produtos_pilares') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.produtos_pilares (" +
                                                "id_produto_pilar integer NOT NULL PRIMARY KEY, " +
                                                "id_entidade integer, id_ciclo integer, id_pilar integer, id_tipo_pontuacao integer, "
                                                +
                                                "peso double precision, nota double precision, analise varchar(MAX), " +
                                                "motivacao_peso varchar(MAX), motivacao_nota varchar(MAX), " +
                                                "id_supervisor integer, id_auditor integer, id_author integer, " +
                                                "criado_em datetime, id_versao_origem integer, id_status integer) END ",
                                schema, schema);
                return sql;
        }

        // Table PRODUTOS_COMPONENTES
        public static String createProdutosComponentesTable(String schema) {
                String sql = String.format(
                                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.produtos_componentes') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.produtos_componentes (" +
                                                "id_produto_componente integer NOT NULL PRIMARY KEY, " +
                                                "id_entidade integer, id_ciclo integer, id_pilar integer, id_componente integer, "
                                                +
                                                "id_tipo_pontuacao integer, peso double precision, nota double precision, "
                                                +
                                                "analise varchar(MAX), motivacao_peso varchar(MAX), motivacao_nota varchar(MAX), "
                                                +
                                                "motivacao_reprogramacao varchar(MAX), justificativa varchar(MAX), " +
                                                "id_supervisor integer, id_auditor integer, id_author integer, " +
                                                "inicia_em date, termina_em date, criado_em datetime, id_versao_origem integer, id_status integer) END ",
                                schema, schema);
                return sql;
        }

        // Table PRODUTOS_ELEMENTOS
        public static String createProdutosElementosTable(String schema) {
                String sql = String.format(
                                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.produtos_elementos') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.produtos_elementos (" +
                                                "id_produto_elemento integer NOT NULL PRIMARY KEY, " +
                                                "id_entidade integer, id_ciclo integer, id_pilar integer, " +
                                                "id_componente integer, id_plano integer, id_tipo_nota integer, id_elemento integer, "
                                                +
                                                "id_tipo_pontuacao integer, peso double precision, nota double precision, "
                                                +
                                                "analise varchar(MAX), motivacao_peso varchar(MAX), motivacao_nota varchar(MAX), "
                                                +
                                                "justificativa varchar(MAX), id_supervisor integer, id_auditor integer, "
                                                +
                                                "id_author integer, criado_em datetime, id_versao_origem integer, id_status integer) END ",
                                schema, schema);
                return sql;
        }

        // Table PRODUTOS_ITENS
        public static String createProdutosItensTable(String schema) {
                String sql = String
                                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.produtos_itens') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.produtos_itens (" +
                                                "id_produto_item integer NOT NULL PRIMARY KEY, " +
                                                "id_entidade integer, id_ciclo integer, id_pilar integer, id_componente integer, "
                                                +
                                                "id_plano integer, id_tipo_nota integer, id_elemento integer, id_item integer, "
                                                +
                                                "analise varchar(MAX), anexo varchar(MAX), id_author integer, " +
                                                "criado_em datetime, id_versao_origem integer, id_status integer) END ",
                                                schema, schema);
                return sql;
        }

        // Table PRODUTOS_PLANOS
        public static String createProdutosPlanosTable(String schema) {
                String sql = String.format(
                                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.produtos_planos') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.produtos_planos (" +
                                                "id_produto_plano integer NOT NULL PRIMARY KEY, " +
                                                "id_entidade integer, id_ciclo integer, id_pilar integer, id_componente integer, id_plano integer, "
                                                +
                                                "id_tipo_pontuacao integer, peso double precision, nota double precision, "
                                                +
                                                "analise varchar(MAX), motivacao_peso varchar(MAX), motivacao_nota varchar(MAX), "
                                                +
                                                "id_author integer, criado_em datetime, id_versao_origem integer, id_status integer) END ",
                                schema, schema);
                return sql;
        }

        // Table PRODUTOS_TIPOS_NOTAS
        public static String createProdutosTiposNotasTable(String schema) {
                String sql = String.format(
                                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.produtos_tipos_notas') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.produtos_tipos_notas (" +
                                                "id_produto_tipo_nota integer NOT NULL PRIMARY KEY, " +
                                                "id_entidade integer, id_ciclo integer, id_pilar integer, id_componente integer, id_plano integer, "
                                                +
                                                "id_tipo_nota integer, id_tipo_pontuacao integer, peso double precision, nota double precision, "
                                                +
                                                "analise varchar(MAX), anexo varchar(MAX), id_author integer, " +
                                                "criado_em datetime, id_versao_origem integer, id_status integer) END ",
                                schema, schema);
                return sql;
        }

}
