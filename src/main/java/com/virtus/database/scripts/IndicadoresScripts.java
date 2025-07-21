package com.virtus.database.scripts;

public class IndicadoresScripts {

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

        public static String insertIndicadoresIfNotExists(String schema) {
                class IndicadorConformidade {
                        int id;
                        String sigla;
                        String nome;
                        String descricao;

                        IndicadorConformidade(int id, String sigla, String nome, String descricao) {
                                this.id = id;
                                this.sigla = sigla;
                                this.nome = nome;
                                this.descricao = descricao;
                        }
                }

                IndicadorConformidade[] indicadores = new IndicadorConformidade[] {
                                new IndicadorConformidade(1, "IS", "Indicador IS", "Indicador de Solvência"),
                                new IndicadorConformidade(2, "IARD_ANT", "Indicador IARD_ANT",
                                                "Indicador de Acompanhamento de Receitas e Despesas"),
                                new IndicadorConformidade(3, "IDRG", "Indicador IDRG",
                                                "Indicador de Acompanhamento da Representatividade das Despesas em Relação aos Recursos Garantidores"),
                                new IndicadorConformidade(4, "TADE_TCRP_E", "Indicador TADE_TCRP_E",
                                                "Indicador de acompanhamento das taxas de administração e carregamento da Entidade"),
                                new IndicadorConformidade(5, "TADE_EFPC", "Indicador TADE_EFPC",
                                                "Indicador de acompanhamento da taxa de administração da Entidade"),
                                new IndicadorConformidade(6, "TCRP_EFPC", "Indicador TCRP_EFPC",
                                                "Indicador de acompanhamento da taxa de carregamento da Entidade"),
                                new IndicadorConformidade(7, "TADE_TCRP_P", "Indicador TADE_TCRP_P",
                                                "Indicador de acompanhamento das taxas de administração e carregamento de plano"),
                                new IndicadorConformidade(8, "TADE_PLANO", "Indicador TADE_PLANO",
                                                "Indicador de acompanhamento da taxa de administração de plano"),
                                new IndicadorConformidade(9, "TCRP_PLANO", "Indicador TCRP_PLANO",
                                                "Indicador de acompanhamento da taxa de carregamento de plano"),
                                new IndicadorConformidade(10, "IRR", "Indicador IRR",
                                                "Indicador de risco de reinvestimento de planos"),
                                new IndicadorConformidade(11, "ERFN", "Indicador ERFN",
                                                "Estimativa do Reinvestimento - Fluxo Negativo"),
                                new IndicadorConformidade(12, "RAFP", "Indicador RAFP",
                                                "Representatividade dos Ativos considerados em relação ao RG – Fluxo Positivo"),
                                new IndicadorConformidade(13, "PABD", "Indicador PABD",
                                                "Proporção dos Ativos considerados atribuíveis às Provisões Matemáticas BD"),
                                new IndicadorConformidade(14, "IDTE", "Indicador IDTE",
                                                "Indicador de Acompanhamento de Déficit Técnico a Equacionar"),
                                new IndicadorConformidade(15, "VADU", "Indicador VADU", "Variação da Duration"),
                                new IndicadorConformidade(16, "PEDE", "Indicador PEDE",
                                                "Pendência de Déficit a Equacionar"),
                                new IndicadorConformidade(17, "IDP", "Indicador IDP",
                                                "Indicador de Dependência do Patrocinador"),
                                new IndicadorConformidade(18, "IPTM", "Indicador IPTM",
                                                "Indicador de Acompanhamento da Premissa de Tábua de Mortalidade Geral"),
                                new IndicadorConformidade(19, "IRA", "Indicador IRA", "Índice de Risco Atuarial"),
                                new IndicadorConformidade(20, "IADE", "Indicador IADE",
                                                "Indicador de Acompanhamento de Déficit Equacionado"),
                                new IndicadorConformidade(21, "IASL", "Indicador IASL",
                                                "Indicador de Ativos Sem Liquidez"),
                                new IndicadorConformidade(22, "RAPOD", "Indicador RAPOD",
                                                "Representatividade de Atrasos, Ativos de Emissão do Patrocinador, Outros e Depósitos Judiciais"),
                                new IndicadorConformidade(23, "RESC", "Indicador RESC",
                                                "Redução de Saldo de Contratos"),
                                new IndicadorConformidade(24, "ATAR", "Indicador ATAR", "Atrasos na Arrecadação"),
                                new IndicadorConformidade(25, "IAP", "Indicador IAP",
                                                "Indicador de Acompanhamento da Performance"),
                                new IndicadorConformidade(26, "IAR", "Indicador IAR",
                                                "Indicador de Acompanhamento da Rentabilidade"),
                                new IndicadorConformidade(27, "IPTJ", "Indicador IPTJ",
                                                "Indicador de verificação de utilização da premissa de taxa de juros"),
                                new IndicadorConformidade(28, "DTA", "Indicador DTA", "Desvio da Taxa Atuarial"),
                                new IndicadorConformidade(29, "RITA", "Indicador RITA",
                                                "Risco de Insolvência por Desajuste da Taxa Atuarial"),
                                new IndicadorConformidade(30, "IRJ", "Indicador IRJ", "Indicador de Risco Jurídico"),
                                new IndicadorConformidade(31, "IFP", "Indicador IFP",
                                                "Indicador de Fundos Previdenciais"),
                                new IndicadorConformidade(32, "IOGA", "Indicador IOGA",
                                                "Indicador de Outros Valores da Gestão Administrativa"),
                                new IndicadorConformidade(33, "IARD", "Indicador IARD",
                                                "Acompanhamento de Receitas e Despesas"),
                                new IndicadorConformidade(34, "ILA_ILR", "Indicador ILA_ILR",
                                                "Indicador que mede o grau de exposição do Plano de Benefícios ao risco de liquidez"),
                                new IndicadorConformidade(35, "ILA", "Indicador ILA", "Índice de Liquidez Ampla"),
                                new IndicadorConformidade(36, "ILR", "Indicador ILR", "Índice de Liquidez Restrita"),
                                new IndicadorConformidade(37, "IEC", "Indicador IEC",
                                                "Identifica mutações relevantes nos maiores segmentos da carteira de investimentos do plano"),
                                new IndicadorConformidade(38, "IASFP", "Indicador IASFP",
                                                "Indicador de Acompanhamento de Superávit e Fundos Previdenciais"),
                                new IndicadorConformidade(39, "AS", "Indicador AS", "Acompanhamento de Superávit"),
                                new IndicadorConformidade(40, "NRO", "Indicador NRO",
                                                "Acompanhamento da Necessidade de Revisão Obrigatória"),
                                new IndicadorConformidade(41, "AFRP", "Indicador AFRP",
                                                "Acompanhamento do Fundo Previdencial de Revisão do Plano"),
                };

                StringBuilder sb = new StringBuilder();
                for (IndicadorConformidade ind : indicadores) {
                        sb.append(String.format(
                                        "IF NOT EXISTS (SELECT 1 FROM %s.indicadores WHERE sigla_indicador = '%s')\n" +
                                                        "BEGIN\n" +
                                                        "    INSERT INTO %s.indicadores ([id_indicador], [sigla_indicador], [nome_indicador], [descricao_indicador], [criado_em], [id_author])\n"
                                                        +
                                                        "    VALUES (%d, '%s', '%s', '%s', GETDATE(), 1);\n" +
                                                        "END;\n\n",
                                        schema, ind.sigla,
                                        schema, ind.id, ind.sigla, ind.nome, ind.descricao));
                }

                return sb.toString();
        }

}
