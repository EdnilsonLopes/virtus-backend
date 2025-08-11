package com.virtus.database.scripts;

public class UniqueKeysScripts {
    public static String createUniqueKeysIfNotExists(String schema) {
        UniqueKeyDefinition[] uniqueKeys = new UniqueKeyDefinition[] {
                new UniqueKeyDefinition(
                        "action_status_unique_key",
                        "actions_status", "id_action, id_origin_status, id_destination_status"),
                new UniqueKeyDefinition(
                        "action_status_unique_key",
                        "actions_status",
                        "id_action, id_origin_status, id_destination_status"),
                new UniqueKeyDefinition(
                        "feature_role_unique_key",
                        "features_roles",
                        "id_role, id_feature"),
                new UniqueKeyDefinition(
                        "username_unique_key",
                        "users",
                        "username"),
                new UniqueKeyDefinition(
                        "action_role_unique_key",
                        "activities_roles",
                        "id_activity, id_role"),
                new UniqueKeyDefinition(
                        "features_activities_unique_key",
                        "features_activities",
                        "id_activity, id_feature"),
                new UniqueKeyDefinition(
                        "jurisdicoes_unique_key",
                        "jurisdicoes",
                        "id_escritorio, id_entidade"),
                new UniqueKeyDefinition(
                        "membros_unique_key",
                        "membros",
                        "id_escritorio, id_usuario"),
                new UniqueKeyDefinition("action_status_unique_key", "actions_status",
                        "id_action, id_origin_status, id_destination_status"),
                new UniqueKeyDefinition("feature_role_unique_key", "features_roles", "id_role, id_feature"),
                new UniqueKeyDefinition("username_unique_key", "users", "username"),
                new UniqueKeyDefinition("action_role_unique_key", "activities_roles", "id_activity, id_role"),
                new UniqueKeyDefinition("features_activities_unique_key", "features_activities",
                        "id_activity, id_feature"),
                new UniqueKeyDefinition("jurisdicoes_unique_key", "jurisdicoes", "id_escritorio, id_entidade"),
                new UniqueKeyDefinition("membros_unique_key", "membros", "id_escritorio, id_usuario"),
                new UniqueKeyDefinition("ciclos_entidades_unique_key", "ciclos_entidades", "id_entidade, id_ciclo"),
                new UniqueKeyDefinition("pilares_ciclos_unique_key", "pilares_ciclos", "id_ciclo, id_pilar"),
                new UniqueKeyDefinition("componentes_pilares_unique_key", "componentes_pilares",
                        "id_pilar, id_componente"),
                new UniqueKeyDefinition("elementos_componentes_unique_key", "elementos_componentes",
                        "id_componente, id_elemento, id_tipo_nota"),
                new UniqueKeyDefinition("tipos_notas_componentes_unique_key", "tipos_notas_componentes",
                        "id_componente, id_tipo_nota"),
                new UniqueKeyDefinition("produtos_ciclos_unique_key", "produtos_ciclos", "id_entidade, id_ciclo"),
                new UniqueKeyDefinition("produtos_pilares_unique_key", "produtos_pilares",
                        "id_entidade, id_ciclo, id_pilar"),
                new UniqueKeyDefinition("produtos_componentes_unique_key", "produtos_componentes",
                        "id_entidade, id_ciclo, id_pilar, id_componente"),
                new UniqueKeyDefinition("produtos_planos_unique_key", "produtos_planos",
                        "id_entidade, id_ciclo, id_pilar, id_componente, id_plano"),
                new UniqueKeyDefinition("produtos_tipos_notas_unique_key", "produtos_tipos_notas",
                        "id_entidade, id_ciclo, id_pilar, id_componente, id_plano, id_tipo_nota"),
                new UniqueKeyDefinition("produtos_elementos_unique_key", "produtos_elementos",
                        "id_entidade, id_ciclo, id_pilar, id_componente, id_plano, id_tipo_nota, id_elemento"),
                new UniqueKeyDefinition("produtos_itens_unique_key", "produtos_itens",
                        "id_entidade, id_ciclo, id_pilar, id_componente, id_plano, id_tipo_nota, id_elemento, id_item"),
                new UniqueKeyDefinition("notas_indicadores_ref_ind_cnpb_unique_key", "notas_indicadores",
                        "data_referencia, id_indicador, cnpb")
        };

        StringBuilder sqlBuilder = new StringBuilder();

        for (UniqueKeyDefinition uk : uniqueKeys) {
            sqlBuilder.append("IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = '")
                    .append(uk.name).append("')\n")
                    .append("BEGIN\n")
                    .append("    ALTER TABLE ").append(schema).append(".").append(uk.sourceTable).append("\n")
                    .append("    ADD CONSTRAINT ").append(uk.name)
                    .append(" UNIQUE (").append(uk.sourceColumns).append(");\n")
                    .append("END;\n\n");
        }

        return sqlBuilder.toString();
    }
}

class UniqueKeyDefinition {
    String name;
    String sourceTable;
    String sourceColumns;

    public UniqueKeyDefinition(String name, String sourceTable, String sourceColumns) {
        this.name = name;
        this.sourceTable = sourceTable;
        this.sourceColumns = sourceColumns;
    }
}
