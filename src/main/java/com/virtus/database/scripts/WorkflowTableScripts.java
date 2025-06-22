package com.virtus.database.scripts;

public class WorkflowTableScripts {
        // Table ACTIONS
        public static String createActionsTable(String schema) {
                String sql = String.format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE name = 'actions' " +
                                "AND type in (N'U')) BEGIN CREATE TABLE %s.actions ( " +
                                "id_action integer NOT NULL PRIMARY KEY, " +
                                "name varchar(255) NOT NULL, " +
                                "id_origin_status integer, " +
                                "id_destination_status integer, " +
                                "other_than bit, " +
                                "description varchar(MAX), " +
                                "id_author integer, " +
                                "created_at datetime, " +
                                "id_versao_origem integer, " +
                                "id_status integer) END ", schema);
                return sql;
        }

        // Table ACTIONS_STATUS
        public static String createActionsStatusTable(String schema) {
                String sql = String
                                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.actions_status') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.actions_status (" +
                                                "id_action_status integer NOT NULL PRIMARY KEY, " +
                                                "id_action integer, " +
                                                "id_origin_status integer, " +
                                                "id_destination_status integer) END ", schema, schema);
                return sql;
        }

        // Table ACTIVITIES
        public static String createActivitiesTable(String schema) {
                String sql = String
                                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.activities') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.activities (" +
                                                "id_activity integer NOT NULL PRIMARY KEY, " +
                                                "id_workflow integer, " +
                                                "id_action integer, " +
                                                "id_expiration_action integer, " +
                                                "expiration_time_days integer, " +
                                                "start_at datetime, " +
                                                "end_at datetime) END ", schema, schema);
                return sql;
        }

        // Table ACTIVITIES_ROLES
        public static String createActivitiesRolesTable(String schema) {
                String sql = String.format(
                                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.activities_roles') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.activities_roles (" +
                                                "id_activity_role integer NOT NULL PRIMARY KEY, " +
                                                "id_activity integer, " +
                                                "id_role integer) END ",
                                schema, schema);
                return sql;
        }

        // Table FEATURES
        public static String createFeaturesTable(String schema) {
                String sql = String
                                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.features') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.features (" +
                                                "id_feature integer NOT NULL PRIMARY KEY, " +
                                                "name varchar(255) NOT NULL, " +
                                                "code varchar(255) NOT NULL, " +
                                                "description varchar(MAX), " +
                                                "id_author integer, " +
                                                "created_at datetime, " +
                                                "id_versao_origem integer, " +
                                                "id_status integer) END ", schema, schema);
                return sql;
        }

        // Table FEATURES_ACTIVITIES
        public static String createFeaturesActivitiesTable(String schema) {
                String sql = String.format(
                                "IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.features_activities') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.features_activities (" +
                                                "id_feature_activity integer NOT NULL PRIMARY KEY, " +
                                                "id_feature integer, " +
                                                "id_activity integer) END ",
                                schema, schema);
                return sql;
        }

        // Table FEATURES_ROLES
        public static String createFeaturesRolesTable(String schema) {
                String sql = String
                                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.features_roles') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.features_roles (" +
                                                "id_feature_role integer NOT NULL PRIMARY KEY, " +
                                                "id_feature integer NOT NULL, " +
                                                "id_role integer NOT NULL) END ", schema, schema);
                return sql;
        }

        // Table ROLES
        public static String createRolesTable(String schema) {
                String sql = String
                                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.roles') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.roles (" +
                                                "id_role integer NOT NULL PRIMARY KEY, " +
                                                "name varchar(255) NOT NULL, description varchar(MAX), " +
                                                "id_author integer, created_at datetime, " +
                                                "id_versao_origem integer, id_status integer) END ", schema, schema);
                return sql;
        }

        // Table STATUS
        public static String createStatusTable(String schema) {
                String sql = String
                                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.status') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.status (" +
                                                "id_status integer NOT NULL PRIMARY KEY, " +
                                                "name varchar(255) NOT NULL, description varchar(MAX), " +
                                                "id_author integer, created_at datetime, " +
                                                "id_versao_origem integer, status_id integer, stereotype varchar(255)) END ",
                                                schema, schema);
                return sql;
        }

        // Table WORKFLOWS
        public static String createWorkflowsTable(String schema) {
                String sql = String
                                .format("IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE object_id = OBJECT_ID(N'%s.workflows') "
                                                +
                                                "AND type in (N'U')) BEGIN CREATE TABLE %s.workflows (" +
                                                "id_workflow integer NOT NULL PRIMARY KEY, " +
                                                "name varchar(255) NOT NULL, description varchar(MAX), entity_type varchar(50), "
                                                +
                                                "start_at datetime, end_at datetime, id_author integer, " +
                                                "created_at datetime, id_versao_origem integer, id_status integer) END ",
                                                schema, schema);
                return sql;
        }

}
