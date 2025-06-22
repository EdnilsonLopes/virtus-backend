package com.virtus.database.creator;

import org.springframework.stereotype.Component;

import com.virtus.config.AppProperties;
import com.virtus.database.executor.SqlExecutor;
import com.virtus.database.scripts.HistoryTableScripts;
import com.virtus.database.scripts.IndicadoresTableScripts;
import com.virtus.database.scripts.ProductTableScripts;
import com.virtus.database.scripts.SchemaScript;
import com.virtus.database.scripts.TableScripts;
import com.virtus.database.scripts.WorkflowTableScripts;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class SchemaCreator {

    private final SqlExecutor executor;
    private final AppProperties appProperties;

    public void createSchema() {
        executor.execute(SchemaScript.createSchema(appProperties.getSchema()));
    }

    public void createTables() {
        String schema = appProperties.getSchema();

        List<Function<String, String>> tableScripts = List.of(
                (Function<String, String>) TableScripts::createAnotacoesRadaresTable,
                (Function<String, String>) TableScripts::createAnotacoesTable,
                (Function<String, String>) TableScripts::createChamadosTable,
                (Function<String, String>) TableScripts::createChamadosVersoesTable,
                (Function<String, String>) TableScripts::createCiclosTable,
                (Function<String, String>) TableScripts::createCiclosEntidadesTable,
                (Function<String, String>) TableScripts::createComentariosAnotacoesTable,
                (Function<String, String>) TableScripts::createComentariosChamadosTable,
                (Function<String, String>) TableScripts::createComponentesTable,
                (Function<String, String>) TableScripts::createComponentesPilaresTable,
                (Function<String, String>) TableScripts::createElementosTable,
                (Function<String, String>) TableScripts::createElementosComponentesTable,
                (Function<String, String>) TableScripts::createEntidadesTable,
                (Function<String, String>) TableScripts::createEscritoriosTable,
                (Function<String, String>) TableScripts::createIntegrantesTable,
                (Function<String, String>) TableScripts::createItensTable,
                (Function<String, String>) TableScripts::createJurisdicoesTable,
                (Function<String, String>) TableScripts::createMembrosTable,
                (Function<String, String>) TableScripts::createPilaresTable,
                (Function<String, String>) TableScripts::createPilaresCiclosTable,
                (Function<String, String>) TableScripts::createPlanosTable,
                (Function<String, String>) TableScripts::createProcessosTable,
                (Function<String, String>) TableScripts::createRadaresTable,
                (Function<String, String>) TableScripts::createTiposNotasTable,
                (Function<String, String>) TableScripts::createTiposNotasComponentesTable,
                (Function<String, String>) TableScripts::createUsersTable,
                (Function<String, String>) TableScripts::createVersoesTable,
                (Function<String, String>) IndicadoresTableScripts::createIndicadoresTable,
                (Function<String, String>) IndicadoresTableScripts::createNotasIndicadoresTable,
                (Function<String, String>) IndicadoresTableScripts::createNotasAutomaticasTable,
                (Function<String, String>) WorkflowTableScripts::createActionsTable,
                (Function<String, String>) WorkflowTableScripts::createActionsStatusTable,
                (Function<String, String>) WorkflowTableScripts::createActivitiesTable,
                (Function<String, String>) WorkflowTableScripts::createActivitiesRolesTable,
                (Function<String, String>) WorkflowTableScripts::createFeaturesTable,
                (Function<String, String>) WorkflowTableScripts::createFeaturesActivitiesTable,
                (Function<String, String>) WorkflowTableScripts::createFeaturesRolesTable,
                (Function<String, String>) WorkflowTableScripts::createRolesTable,
                (Function<String, String>) WorkflowTableScripts::createStatusTable,
                (Function<String, String>) WorkflowTableScripts::createWorkflowsTable,
                (Function<String, String>) ProductTableScripts::createProdutosCiclosTable,
                (Function<String, String>) ProductTableScripts::createProdutosComponentesTable,
                (Function<String, String>) ProductTableScripts::createProdutosElementosTable,
                (Function<String, String>) ProductTableScripts::createProdutosItensTable,
                (Function<String, String>) ProductTableScripts::createProdutosPlanosTable,
                (Function<String, String>) ProductTableScripts::createProdutosPilaresTable,
                (Function<String, String>) ProductTableScripts::createProdutosTiposNotasTable,
                (Function<String, String>) HistoryTableScripts::createProdutosCiclosHistoricosTable,
                (Function<String, String>) HistoryTableScripts::createProdutosPilaresHistoricosTable,
                (Function<String, String>) HistoryTableScripts::createProdutosComponentesHistoricosTable,
                (Function<String, String>) HistoryTableScripts::createProdutosPlanosHistoricosTable,
                (Function<String, String>) HistoryTableScripts::createProdutosTiposNotasHistoricosTable,
                (Function<String, String>) HistoryTableScripts::createProdutosElementosHistoricosTable,
                (Function<String, String>) HistoryTableScripts::createProdutosItensHistoricosTable);

        tableScripts.forEach(script -> executor.execute(script.apply(schema)));

    }

    public void loadIndicators() {
        executor.execute(IndicadoresTableScripts.insertIndicadoresIfNotExists(appProperties.getSchema()));  
    }
}
