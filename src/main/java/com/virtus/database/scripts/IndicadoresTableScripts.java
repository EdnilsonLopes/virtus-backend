package com.virtus.database.scripts;

public class IndicadoresTableScripts {

        // Table INDICADORES
        public static String createIndicadoresTable(String schema) {
                String sql = String.format(
                                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.indicadores') "
                                                +
                                                "AND type IN (N'U')) " +
                                                "BEGIN " +
                                                "CREATE TABLE %s.indicadores ( " +
                                                "id_indicador INTEGER NOT NULL PRIMARY KEY, " +
                                                "sigla_indicador VARCHAR(25) NULL, " +
                                                "nome_indicador VARCHAR(200) NULL, " +
                                                "descricao_indicador VARCHAR(400) NULL, " +
                                                "criado_em DATETIME NOT NULL, " +
                                                "id_author int null) " +
                                                "END",
                                schema, schema);
                return sql;
        }

        

        // Table NOTAS_AUTOMATICAS
        public static String createNotasAutomaticasTable(String schema) {
                String sql = String.format(
                                "IF NOT EXISTS (SELECT 1 FROM sys.objects " +
                                                "WHERE object_id = OBJECT_ID(N'%s.notas_automaticas') AND type IN (N'U')) "
                                                +
                                                "BEGIN " +
                                                "CREATE TABLE %s.notas_automaticas ( " +
                                                "id_nota_automatica integer NOT NULL PRIMARY KEY, " +
                                                "cnpb varchar(25) NOT NULL, " +
                                                "data_referencia varchar(6) NULL, " +
                                                "id_componente integer NOT NULL, " +
                                                "nota float NOT NULL, " +
                                                "criado_em datetime NOT NULL) " +
                                                "END",
                                schema, schema);
                return sql;
        }

        // Insert Indicators if Not Exists
        public static String insertIndicadoresIfNotExists(String schema) {
                return String.format("" +
                                "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = 'ILA_ILR') " +
                                "INSERT INTO %s.indicadores VALUES (1, 'ILA_ILR', 'Indicadores de Liquidez de Ativos / Indicadores de Liquidez de Recursos', "
                                +
                                "'Refere-se à capacidade da entidade de transformar seus ativos em recursos líquidos suficientes para cumprir obrigações.', GETDATE(), 1); "
                                +

                                "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = 'IRR') " +
                                "INSERT INTO %s.indicadores VALUES (2, 'IRR', 'Indicador de Risco de Renda', " +
                                "'Pode avaliar a exposição a ativos de renda variável ou a volatilidade da carteira de investimentos.', GETDATE(), 1); "
                                +

                                "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = 'IASL') " +
                                "INSERT INTO %s.indicadores VALUES (3, 'IASL', 'Indicador de Alocação em Segmentos de Longo Prazo', "
                                +
                                "'Provavelmente ligado à análise de alocação em ativos estruturados, imobiliários, ou de longo vencimento.', GETDATE(), 1); "
                                +

                                "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = 'IADE') " +
                                "INSERT INTO %s.indicadores VALUES (4, 'IADE', 'Indicador de Adequação de Dívida Exigível', "
                                +
                                "'Pode medir a solvência de curto prazo frente às obrigações assumidas.', GETDATE(), 1); "
                                +

                                "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = 'IDTE') " +
                                "INSERT INTO %s.indicadores VALUES (5, 'IDTE', 'Indicador de Déficit Técnico Estimado', "
                                +
                                "'Relacionado ao valor estimado de déficit atuarial em planos de benefícios.', GETDATE(), 1); "
                                +

                                "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = 'IDP') " +
                                "INSERT INTO %s.indicadores VALUES (6, 'IDP', 'Indicador de Déficit Projetado', " +
                                "'Pode representar a projeção do déficit futuro com base em premissas atuariais e econômicas.', GETDATE(), 1); "
                                +

                                "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = 'IPTM') " +
                                "INSERT INTO %s.indicadores VALUES (7, 'IPTM', 'Indicador de Projeção Técnico-Atuarial de Massa', "
                                +
                                "'Possivelmente relacionado à consistência dos resultados atuariais frente ao perfil demográfico dos participantes.', GETDATE(), 1); "
                                +

                                "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = 'IDRG') " +
                                "INSERT INTO %s.indicadores VALUES (8, 'IDRG', 'Indicador de Despesas Relativas à Gestão', "
                                +
                                "'Mede o peso das despesas administrativas sobre o patrimônio ou contribuição total.', GETDATE(), 1); "
                                +

                                "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = 'TADE_TCRP_P') " +
                                "INSERT INTO %s.indicadores VALUES (9, 'TADE_TCRP_P', 'Taxa Administrativa / Taxa de Custeio Relativa por Participante', "
                                +
                                "'Componente complexo, pode representar combinação de taxa de administração (TADE), taxa de custeio (TCRP) por participante (P).', GETDATE(), 1); "
                                +

                                "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = 'IAP') " +
                                "INSERT INTO %s.indicadores VALUES (10, 'IAP', 'Índice de Acompanhamento de Performance', "
                                +
                                "'Indicador que mede a aderência dos resultados atuarialmente projetados em relação à performance real dos planos, contribuindo para a composição da nota do componente Atuarial.', GETDATE(), 1);",
                                schema, schema, schema, schema, schema, schema, schema, schema,
                                schema, schema, schema, schema, schema, schema, schema, schema,
                                schema, schema, schema, schema, schema, schema, schema, schema,
                                schema, schema, schema, schema);
        }
}
