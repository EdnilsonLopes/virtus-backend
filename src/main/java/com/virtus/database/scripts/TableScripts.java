package com.virtus.database.scripts;

public class TableScripts {

    // Table ANOTACOES
    public static String createAnotacoesTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.anotacoes') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.anotacoes (" +
                        "id_anotacao integer NOT NULL PRIMARY KEY, " +
                        "id_entidade integer, id_ciclo integer, id_pilar integer, id_componente integer, " +
                        "id_plano integer, id_tipo_nota integer, id_elemento integer, id_item integer, " +
                        "assunto varchar(255) NOT NULL, risco char(1), tendencia char(1), " +
                        "id_relator integer, id_responsavel integer, descricao varchar(MAX), matriz varchar(255), " +
                        "id_author integer, criado_em datetime, id_versao_origem integer, id_status integer) END ",
                schema, schema);
        return sql;
    }

    // Table ANOTACOES_RADARES
    public static String createAnotacoesRadaresTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.anotacoes_radares') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.anotacoes_radares (" +
                        "id_anotacao_radar integer NOT NULL PRIMARY KEY, " +
                        "id_radar integer, id_anotacao integer, observacoes varchar(MAX), registro_ata varchar(MAX), " +
                        "ultima_atualizacao datetime, id_ultimo_atualizador integer, " +
                        "id_author integer, criado_em datetime, id_versao_origem integer, id_status integer) END ",
                schema, schema);
        return sql;
    }

    // Table CHAMADOS
    public static String createChamadosTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.chamados') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.chamados (" +
                        "id_chamado integer NOT NULL PRIMARY KEY, " +
                        "titulo varchar(255) NOT NULL, " +
                        "descricao varchar(MAX), " +
                        "acompanhamento varchar(MAX), " +
                        "id_responsavel integer, " +
                        "id_relator integer, " +
                        "id_tipo_chamado char(1), " +
                        "id_prioridade char(1), " +
                        "estimativa integer, " +
                        "inicia_em datetime, " +
                        "pronto_em datetime, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table CHAMADOS_VERSOES
    public static String createChamadosVersoesTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.chamados_versoes') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.chamados_versoes (" +
                        "id_chamado_versao integer NOT NULL PRIMARY KEY, " +
                        "id_activity integer, " +
                        "id_role integer) END ",
                schema, schema);
        return sql;
    }

    // Table CICLOS
    public static String createCiclosTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.ciclos') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.ciclos (" +
                        "id_ciclo integer NOT NULL PRIMARY KEY, " +
                        "nome varchar(255) NOT NULL, " +
                        "descricao varchar(MAX), " +
                        "referencia varchar(500), " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table CICLOS_ENTIDADES
    public static String createCiclosEntidadesTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.ciclos_entidades') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.ciclos_entidades (" +
                        "id_ciclo_entidade integer NOT NULL PRIMARY KEY, " +
                        "id_ciclo integer, " +
                        "id_entidade integer, " +
                        "tipo_media integer, " +
                        "id_supervisor integer, " +
                        "inicia_em datetime, " +
                        "termina_em datetime, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ",
                schema, schema);
        return sql;
    }

    // Table COMENTARIOS_ANOTACOES
    public static String createComentariosAnotacoesTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.comentarios_anotacoes') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.comentarios_anotacoes (" +
                        "id_comentario_anotacao integer NOT NULL PRIMARY KEY, " +
                        "id_anotacao integer, " +
                        "texto varchar(MAX), " +
                        "referencia varchar(255), " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ",
                schema, schema);
        return sql;
    }

    // Table COMENTARIOS_CHAMADOS
    public static String createComentariosChamadosTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.comentarios_chamados') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.comentarios_chamados (" +
                        "id_comentario_chamado integer NOT NULL PRIMARY KEY, " +
                        "id_chamado integer, " +
                        "texto varchar(MAX), " +
                        "referencia varchar(255), " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ",
                schema, schema);
        return sql;
    }

    // Table COMPONENTES
    public static String createComponentesTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.componentes') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.componentes (" +
                        "id_componente integer NOT NULL PRIMARY KEY, " +
                        "nome varchar(255) NOT NULL, " +
                        "descricao varchar(MAX), " +
                        "referencia varchar(500), " +
                        "pga varchar(1), " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table COMPONENTES_PILARES
    public static String createComponentesPilaresTable(String schema) {
        String sql = String.format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE name = 'componentes_pilares' " +
                "AND type in (N'U')) BEGIN CREATE TABLE %s.componentes_pilares (" +
                "id_componente_pilar integer NOT NULL PRIMARY KEY, " +
                "id_componente integer, " +
                "id_pilar integer, " +
                "tipo_media integer, " +
                "peso_padrao integer, " +
                "sonda varchar(255), " +
                "id_author integer, " +
                "criado_em datetime, " +
                "id_versao_origem integer, " +
                "id_status integer) END ", schema);
        return sql;
    }

    // Table ELEMENTOS
    public static String createElementosTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.elementos') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.elementos (" +
                        "id_elemento integer NOT NULL PRIMARY KEY, " +
                        "nome varchar(255) NOT NULL, " +
                        "descricao varchar(MAX), " +
                        "referencia varchar(500), " +
                        "peso integer DEFAULT 1 NOT NULL, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table ELEMENTOS_COMPONENTES
    public static String createElementosComponentesTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.elementos_componentes') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.elementos_componentes (" +
                        "id_elemento_componente integer NOT NULL PRIMARY KEY, " +
                        "id_componente integer, " +
                        "id_elemento integer, " +
                        "id_tipo_nota integer, " +
                        "peso_padrao integer, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ",
                schema, schema);
        return sql;
    }

    // Table ENTIDADES
    public static String createEntidadesTable(String schema) {
        String sql = String.format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE name = 'entidades' " +
                "AND type in (N'U')) BEGIN CREATE TABLE %s.entidades (" +
                "id_entidade integer NOT NULL PRIMARY KEY, " +
                "nome varchar(255) NOT NULL, " +
                "descricao varchar(MAX), " +
                "sigla varchar(25), " +
                "codigo varchar(MAX), " +
                "situacao varchar(30), " +
                "ESI bit, " +
                "municipio varchar(255), " +
                "sigla_uf char(2), " +
                "id_author integer, " +
                "criado_em datetime, " +
                "id_versao_origem integer, " +
                "id_status integer) END ", schema);
        return sql;
    }

    // Table ESCRITORIOS
    public static String createEscritoriosTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.escritorios') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.escritorios (" +
                        "id_escritorio integer NOT NULL PRIMARY KEY, " +
                        "nome varchar(255) NOT NULL, " +
                        "abreviatura char(4), " +
                        "descricao varchar(MAX), " +
                        "id_chefe integer, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table INDICADORES_COMPONENTES
    public static String createIndicadoresComponentesTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.indicadores_componentes') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.indicadores_componentes (" +
                        "id_indicador_componente integer NOT NULL PRIMARY KEY, " +
                        "id_componente integer, id_indicador integer, peso_padrao double precision, " +
                        "id_author integer, criado_em datetime, id_versao_origem integer, id_status integer) END ",
                schema, schema);
        return sql;
    }

    // Table INTEGRANTES
    public static String createIntegrantesTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.integrantes') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.integrantes (" +
                        "id_integrante integer NOT NULL PRIMARY KEY, " +
                        "id_entidade integer, " +
                        "id_ciclo integer, " +
                        "id_usuario integer, " +
                        "inicia_em datetime, " +
                        "termina_em datetime, " +
                        "motivacao varchar(MAX), " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table ITENS
    public static String createItensTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.itens') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.itens (" +
                        "id_item integer NOT NULL PRIMARY KEY, " +
                        "id_elemento integer, " +
                        "nome varchar(255) NOT NULL, " +
                        "descricao varchar(MAX), " +
                        "referencia varchar(500), " +
                        "criado_em datetime, " +
                        "id_author integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table JURISDICOES
    public static String createJurisdicoesTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.jurisdicoes') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.jurisdicoes (" +
                        "id_jurisdicao integer NOT NULL PRIMARY KEY, " +
                        "id_escritorio integer, " +
                        "id_entidade integer, " +
                        "inicia_em datetime, " +
                        "termina_em datetime, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table MEMBROS
    public static String createMembrosTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.membros') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.membros (" +
                        "id_membro integer NOT NULL PRIMARY KEY, " +
                        "id_escritorio integer, " +
                        "id_usuario integer, " +
                        "inicia_em datetime, " +
                        "termina_em datetime, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table PILARES
    public static String createPilaresTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.pilares') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.pilares (" +
                        "id_pilar integer NOT NULL PRIMARY KEY, " +
                        "nome varchar(255) NOT NULL, " +
                        "descricao varchar(MAX), " +
                        "referencia varchar(500), " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table PILARES_CICLOS
    public static String createPilaresCiclosTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.pilares_ciclos') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.pilares_ciclos (" +
                        "id_pilar_ciclo integer NOT NULL PRIMARY KEY, " +
                        "id_pilar integer, " +
                        "id_ciclo integer, " +
                        "tipo_media integer, " +
                        "peso_padrao double precision, " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table PLANOS
    public static String createPlanosTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.planos') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.planos (" +
                        "id_plano integer NOT NULL PRIMARY KEY, " +
                        "id_entidade integer, " +
                        "nome varchar(255), " +
                        "descricao varchar(MAX), " +
                        "referencia varchar(500), " +
                        "cnpb varchar(255), " +
                        "legislacao varchar(255), " +
                        "situacao varchar(255), " +
                        "recurso_garantidor double precision, " +
                        "id_modalidade character(2), " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    // Table PROCESSOS
    public static String createProcessosTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.processos') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.processos (" +
                        "id_processo integer NOT NULL PRIMARY KEY, " +
                        "id_questao integer, " +
                        "numero varchar(255) NOT NULL, " +
                        "descricao varchar(MAX), " +
                        "referencia varchar(255), " +
                        "id_author integer, " +
                        "criado_em datetime, " +
                        "id_versao_origem integer, " +
                        "id_status integer) END ", schema, schema);
        return sql;
    }

    
    // Table RADARES
    public static String createRadaresTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.radares') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.radares (" +
                        "id_radar integer NOT NULL PRIMARY KEY, " +
                        "nome varchar(255) NOT NULL, descricao varchar(MAX), referencia varchar(255), " +
                        "data_radar datetime, id_author integer, criado_em datetime, " +
                        "id_versao_origem integer, id_status integer) END ", schema, schema);
        return sql;
    }

    // Table TIPOS_NOTAS
    public static String createTiposNotasTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.tipos_notas') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.tipos_notas (" +
                        "id_tipo_nota integer NOT NULL PRIMARY KEY, " +
                        "nome varchar(255) NOT NULL, descricao varchar(MAX), referencia varchar(500), " +
                        "letra char(1) NOT NULL, cor_letra char(6), " +
                        "id_author integer, criado_em datetime, id_versao_origem integer, id_status integer) END ",
                schema, schema);
        return sql;
    }

    // Table TIPOS_NOTAS_COMPONENTES
    public static String createTiposNotasComponentesTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.tipos_notas_componentes') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.tipos_notas_componentes (" +
                        "id_tipo_nota_componente integer NOT NULL PRIMARY KEY, " +
                        "id_componente integer, id_tipo_nota integer, peso_padrao double precision, " +
                        "id_author integer, criado_em datetime, id_versao_origem integer, id_status integer) END ",
                schema, schema);
        return sql;
    }

    // Table USERS
    public static String createUsersTable(String schema) {
        String sql = String.format(
                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.users') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.users (" +
                        "id_user integer NOT NULL PRIMARY KEY, " +
                        "name varchar(255), username varchar(255) NOT NULL, password varchar(255) NOT NULL, " +
                        "email varchar(255), mobile varchar(255), id_role integer, " +
                        "id_author integer, criado_em datetime, id_versao_origem integer, id_status integer) END ",
                schema, schema);
        return sql;
    }

    // Table VERSOES
    public static String createVersoesTable(String schema) {
        String sql = String
                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.versoes') " +
                        "AND type in (N'U')) BEGIN CREATE TABLE %s.versoes (" +
                        "id_versao integer NOT NULL PRIMARY KEY, " +
                        "nome varchar(255) NOT NULL, objetivo varchar(MAX), definicao_pronto varchar(MAX), " +
                        "inicia_em datetime, termina_em datetime, id_author integer, " +
                        "criado_em datetime, id_versao_origem integer, id_status integer) END ", schema, schema);
        return sql;
    }

}
