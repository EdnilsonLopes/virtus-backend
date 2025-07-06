package com.virtus.config;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.virtus.database.creator.SchemaCreator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.db.initialize", havingValue = "true")
public class DatabaseInitializer {
     private final SchemaCreator schemaCreator;
    @PostConstruct
    public void init() {
        System.out.println("Initializing database...");
        schemaCreator.createSchema();  
        schemaCreator.createTables();  
        schemaCreator.loadIndicators();
        schemaCreator.loadIndicatorScores();
        System.out.println("Database initialization completed.");
    }
}
