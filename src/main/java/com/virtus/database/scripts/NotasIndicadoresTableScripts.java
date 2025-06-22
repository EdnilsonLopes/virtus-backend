package com.virtus.database.scripts;

public class NotasIndicadoresTableScripts {
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

    // Insert Notas Indicadores if Not Exists
    public static String insertNotasIndicadoresIfNotExists(String schema) {
        Object[][] rows = new Object[][] {
                { 1, "2000004318", "202302", 6, "IDP", "SOLVENCIA", 3.8, "2025-06-22 09:38:44.977" },
                { 2, "2002003947", "202308", 4, "IADE", "SOLVENCIA", 3.4, "2025-06-22 09:39:11.710" },
                { 3, "2013001592", "202401", 1, "ILA_ILR", "ATIVOS", 2.0, "2025-06-22 09:39:11.710" },
                { 4, "1991001029", "202406", 6, "IDP", "SOLVENCIA", 2.1, "2025-06-22 09:39:55.173" },
                { 5, "2000001947", "202405", 7, "IPTM", "RESULTADOS", 2.0, "2025-06-22 09:39:55.173" },
                { 6, "2000000819", "202504", 1, "ILA_ILR", "ATIVOS", 1.3, "2025-06-22 09:39:55.173" },
                { 7, "2013001592", "202301", 7, "IPTM", "RESULTADOS", 3.8, "2025-06-22 09:39:55.173" },
                { 8, "2000000983", "202409", 5, "IDTE", "SOLVENCIA", 0.9, "2025-06-22 09:39:55.173" },
                { 9, "4009670029", "202505", 3, "IASL", "ATIVOS", 2.6, "2025-06-22 09:39:55.173" },
                { 10, "2000004318", "202506", 10, "IAP", "ATUARIAL", 0.9, "2025-06-22 09:39:55.173" }
        };

        StringBuilder sb = new StringBuilder();
        for (Object[] row : rows) {
            int id = (int) row[0];
            sb.append(String.format(
                    "IF NOT EXISTS (SELECT 1 FROM %s.notas_indicadores WHERE id_nota_indicador = %d) " +
                            "INSERT INTO %s.notas_indicadores " +
                            "(id_nota_indicador, cnpb, data_referencia, id_indicador, sigla_indicador, tx_componente, nota, criado_em) "
                            +
                            "VALUES (%d, '%s', '%s', %d, '%s', '%s', %.1f, '%s');%n",
                    schema, id,
                    schema,
                    id, row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
        }

        return sb.toString();
    }

}