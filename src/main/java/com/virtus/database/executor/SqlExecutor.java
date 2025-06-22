package com.virtus.database.executor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SqlExecutor {
    private final JdbcTemplate jdbcTemplate;

    public void execute(String sql) {
        try {
            jdbcTemplate.execute(sql);
            System.out.println("Executado com sucesso:\n" + preview(sql));
        } catch (Exception e) {
            System.err.println("Erro ao executar SQL:\n" + preview(sql));
            e.printStackTrace();
        }
    }

    private String preview(String sql) {
        return sql.length() > 80 ? sql.substring(0, 80) + "..." : sql;
    }
}
