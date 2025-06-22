package com.virtus.database.scripts;

public class SchemaScript {
     public static String createSchema(String schema) {
        return String.format("IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = '%s') " +
		" BEGIN EXEC('CREATE SCHEMA %s') END", schema, schema);
    }
}
