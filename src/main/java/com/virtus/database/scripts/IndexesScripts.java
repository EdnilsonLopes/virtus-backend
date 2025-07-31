package com.virtus.database.scripts;

public class IndexesScripts {

    public static String createIndexesIfNotExists(String schema) {
        IndexDefinition[] indexes = new IndexDefinition[]{
                new IndexDefinition("IX_produtos_ciclos_entidade_ciclo", "produtos_ciclos", "id_entidade, id_ciclo"),
                new IndexDefinition("IX_produtos_pilares_entidade_ciclo_pilar", "produtos_pilares", "id_entidade, id_ciclo, id_pilar"),
                new IndexDefinition("IX_elementos_componentes_lookup", "elementos_componentes", "id_elemento, id_tipo_nota, id_componente"),
                new IndexDefinition("IX_componentes_pilares_lookup", "componentes_pilares", "id_componente, id_pilar"),
                new IndexDefinition("IX_pilares_ciclos_lookup", "pilares_ciclos", "id_pilar, id_ciclo"),
                new IndexDefinition("IX_ciclos_entidades_lookup", "ciclos_entidades", "id_ciclo, id_entidade"),
                new IndexDefinition("IX_status_id", "status", "id_status"),
                new IndexDefinition("IX_itens_id", "itens", "id_item"),
                new IndexDefinition("IX_produtos_planos_ordering", "produtos_planos", "id_ciclo, id_pilar, id_componente, id_plano"),
                new IndexDefinition("IX_produtos_planos_entidade_ciclo", "produtos_planos", "id_entidade, id_ciclo, id_pilar, id_componente, id_plano"),
                new IndexDefinition("IX_produtos_componentes_lookup", "produtos_componentes", "id_entidade, id_ciclo, id_pilar, id_componente"),
                new IndexDefinition("IX_produtos_tipos_notas_lookup", "produtos_tipos_notas", "id_entidade, id_ciclo, id_pilar, id_componente, id_plano"),
                new IndexDefinition("IX_produtos_elementos_lookup", "produtos_elementos", "id_entidade, id_ciclo, id_pilar, id_componente, id_plano, id_tipo_nota"),
                new IndexDefinition("IX_produtos_itens_lookup", "produtos_itens", "id_entidade, id_ciclo, id_pilar, id_componente, id_plano, id_tipo_nota, id_elemento"),
                new IndexDefinition("IX_planos_recurso", "planos", "id_plano, recurso_garantidor DESC")
        };

        StringBuilder sqlBuilder = new StringBuilder();

        for (IndexDefinition index : indexes) {
            sqlBuilder.append("IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = '")
                    .append(index.name).append("')\n")
                    .append("BEGIN\n")
                    .append("    CREATE NONCLUSTERED INDEX ").append(index.name)
                    .append(" ON ").append(schema).append(".").append(index.table)
                    .append(" (").append(index.columns).append(");\n")
                    .append("END;\n\n");
        }

        return sqlBuilder.toString();
    }
}

class IndexDefinition {
    String name;
    String table;
    String columns;

    public IndexDefinition(String name, String table, String columns) {
        this.name = name;
        this.table = table;
        this.columns = columns;
    }
}
