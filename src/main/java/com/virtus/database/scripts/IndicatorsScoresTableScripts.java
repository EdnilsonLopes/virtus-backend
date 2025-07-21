package com.virtus.database.scripts;

import java.util.Locale;

public class IndicatorsScoresTableScripts {
        // Table NOTAS_INDICADORES
        public static String createNotasIndicadoresTable(String schema) {
                String sql = String.format(
                                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.notas_indicadores') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.notas_indicadores (" +
                                                "id_nota_indicador integer NOT NULL PRIMARY KEY, " +
                                                "cnpb varchar(25) NOT NULL, " +
                                                "data_referencia varchar(6) NULL, " +
                                                "id_indicador integer NOT NULL, " +
                                                "sigla_indicador varchar(25) NULL, " +
                                                "tx_componente varchar(25) NOT NULL, " +
                                                "nota float NOT NULL, " +
                                                "criado_em datetime NOT NULL) END ",
                                schema, schema);
                return sql;
        }

        public static String insertNotasIndicadoresIfNotExists(String schema) {
                Object[][] rows = new Object[][] {
                                { "1985000792", "202312", 1, "ILA_ILR", "ATIVOS", 3.5, "2025-06-23 10:00:00.000" },
                                { "1985000792", "202312", 2, "IRR", "ATIVOS", 2.5, "2025-06-23 10:00:00.000" },
                                { "1985000792", "202312", 3, "IASL", "ATIVOS", 2.9, "2025-06-23 10:00:00.000" },
                                // ... (adicione mais linhas conforme necessário)
                };

                StringBuilder sb = new StringBuilder();
                int idCounter = 1000;

                for (Object[] row : rows) {
                        int id = idCounter++;
                        sb.append(String.format(Locale.US,
                                        "IF NOT EXISTS (SELECT 1 FROM %s.notas_indicadores WHERE id_nota_indicador = %d)\n"
                                                        +
                                                        "BEGIN\n" +
                                                        "    INSERT INTO %s.notas_indicadores " +
                                                        "(id_nota_indicador, cnpb, data_referencia, id_indicador, sigla_indicador, tx_componente, nota, criado_em)\n"
                                                        +
                                                        "    VALUES (%d, '%s', '%s', %d, '%s', '%s', %.1f, CONVERT(datetime, '%s', 121));\n" +
                                                        "END;\n\n",
                                        schema, id, schema, id,
                                        row[0], row[1], row[2], row[3], row[4], row[5], row[6]));
                }

                return sb.toString();
        }

}