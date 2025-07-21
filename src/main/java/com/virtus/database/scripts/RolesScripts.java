package com.virtus.database.scripts;

public class RolesScripts {

        public static String insertRolesIfNotExists(String schema) {
                class Role {
                        int id;
                        String name;
                        String description;

                        Role(int id, String name, String description) {
                                this.id = id;
                                this.name = name;
                                this.description = description;
                        }
                }

                Role[] roles = new Role[] {
                                new Role(1, "Admin", "Admin"),
                                new Role(2, "Chefe", "Chefe"),
                                new Role(3, "Supervisor", "Supervisor"),
                                new Role(4, "Auditor", "Auditor"),
                                new Role(5, "Visualizador", "Visualizador"),
                                new Role(6, "Desenvolvedor", "Desenvolvedor")
                };

                StringBuilder sb = new StringBuilder();
                for (Role role : roles) {
                        sb.append(String.format(
                                        "IF NOT EXISTS (SELECT id_role FROM %s.roles WHERE name = '%s')\n" +
                                                        "BEGIN\n" +
                                                        "    INSERT INTO %s.roles (id_role, name, description, created_at)\n"
                                                        +
                                                        "    VALUES (%d, '%s', '%s', GETDATE());\n" +
                                                        "END;\n\n",
                                        schema, role.name,
                                        schema, role.id, role.name.replace("'", "''"),
                                        role.description.replace("'", "''")));
                }

                return sb.toString();
        }

        public static String insertRoleFeatures(String schema) {
                StringBuilder sb = new StringBuilder();
                int id = 1;

                // Role 1 (Admin) - vincular todas as 46 features
                sb.append("INSERT INTO ").append(schema)
                                .append(".features_roles (id_feature_role, id_role, id_feature)\n");
                for (int i = 1; i <= 46; i++) {
                        sb.append(String.format(
                                        "SELECT %d, 1, %d WHERE NOT EXISTS (SELECT 1 FROM %s.features_roles WHERE id_feature = %d AND id_role = 1) UNION\n",
                                        id++, i, schema, i));
                }
                sb.setLength(sb.length() - " UNION\n".length());
                sb.append(";\n\n");

                // Role 6 (Desenvolvedor) - mesmas 46 features
                sb.append("INSERT INTO ").append(schema)
                                .append(".features_roles (id_feature_role, id_role, id_feature)\n");
                for (int i = 1; i <= 46; i++) {
                        sb.append(String.format(
                                        "SELECT %d, 6, %d WHERE NOT EXISTS (SELECT 1 FROM %s.features_roles WHERE id_feature = %d AND id_role = 6) UNION\n",
                                        id++, i, schema, i));
                }
                sb.setLength(sb.length() - " UNION\n".length());
                sb.append(";\n\n");

                // Role 2 (Chefe)
                sb.append(String.format(
                                "INSERT INTO %s.features_roles (id_feature_role, id_role, id_feature)\n" +
                                                "SELECT %d + ROW_NUMBER() OVER (ORDER BY a.id_feature) - 1, 2, a.id_feature FROM %s.features a\n"
                                                +
                                                "WHERE NOT EXISTS (\n" +
                                                "    SELECT 1 FROM %s.features_roles b WHERE b.id_role = 2 AND b.id_feature = a.id_feature\n"
                                                +
                                                ")\n" +
                                                "AND a.code IN ('designarEquipes','distribuirAtividades','avaliarPlanos','viewMatriz','listEntidades','viewEntidade','homeSupervisor','homeChefe','homeAuditor','listChamados','createChamado','listAnotacoes','createAnotacao');\n\n",
                                schema, id, schema, schema));
                id += 13;

                // Role 3 (Supervisor)
                sb.append(String.format(
                                "INSERT INTO %s.features_roles (id_feature_role, id_role, id_feature)\n" +
                                                "SELECT %d + ROW_NUMBER() OVER (ORDER BY a.id_feature) - 1, 3, a.id_feature FROM %s.features a\n"
                                                +
                                                "WHERE NOT EXISTS (\n" +
                                                "    SELECT 1 FROM %s.features_roles b WHERE b.id_role = 3 AND b.id_feature = a.id_feature\n"
                                                +
                                                ")\n" +
                                                "AND a.code IN ('distribuirAtividades','avaliarPlanos','viewMatriz','listEntidades','viewEntidade','homeSupervisor','listChamados','createChamado','listAnotacoes','createAnotacao');\n\n",
                                schema, id, schema, schema));
                id += 10;

                // Role 4 (Auditor)
                sb.append(String.format(
                                "INSERT INTO %s.features_roles (id_feature_role, id_role, id_feature)\n" +
                                                "SELECT %d + ROW_NUMBER() OVER (ORDER BY a.id_feature) - 1, 4, a.id_feature FROM %s.features a\n"
                                                +
                                                "WHERE NOT EXISTS (\n" +
                                                "    SELECT 1 FROM %s.features_roles b WHERE b.id_role = 4 AND b.id_feature = a.id_feature\n"
                                                +
                                                ")\n" +
                                                "AND a.code IN ('avaliarPlanos','viewMatriz','listEntidades','viewEntidade','homeAuditor','listChamados','createChamado','listAnotacoes');\n\n",
                                schema, id, schema, schema));
                id += 8;

                // Role 5 (Visualizador)
                sb.append(String.format(
                                "INSERT INTO %s.features_roles (id_feature_role, id_role, id_feature)\n" +
                                                "SELECT %d + ROW_NUMBER() OVER (ORDER BY a.id_feature) - 1, 5, a.id_feature FROM %s.features a\n"
                                                +
                                                "WHERE NOT EXISTS (\n" +
                                                "    SELECT 1 FROM %s.features_roles b WHERE b.id_role = 5 AND b.id_feature = a.id_feature\n"
                                                +
                                                ")\n" +
                                                "AND (SUBSTRING(a.code, 1, 4) = 'list' OR a.code IN ('createChamado','viewEntidade','viewMatriz'));\n",
                                schema, id, schema, schema));
                // id += número de linhas inseridas por essa última query, se necessário

                return sb.toString();
        }

}
