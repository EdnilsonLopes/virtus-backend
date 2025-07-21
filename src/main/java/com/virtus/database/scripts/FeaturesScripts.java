package com.virtus.database.scripts;

public class FeaturesScripts {
    public static String insertFeaturesIfNotExists(String schema) {
        class Feature {
            int id;
            String name;
            String code;
    
            Feature(int id, String name, String code) {
                this.id = id;
                this.name = name;
                this.code = code;
            }
        }
    
        Feature[] features = new Feature[] {
            new Feature(1, "Listar Workflows", "listWorkflows"),
            new Feature(2, "Criar Workflow", "createWorkflow"),
            new Feature(3, "Listar Elementos", "listElementos"),
            new Feature(4, "Criar Elemento", "createElemento"),
            new Feature(5, "Listar Usuários", "listUsers"),
            new Feature(6, "Criar Usuário", "createUser"),
            new Feature(7, "Listar Perfis", "listRoles"),
            new Feature(8, "Criar Perfil", "createRole"),
            new Feature(9, "Listar Status", "listStatus"),
            new Feature(10, "Criar Status", "createStatus"),
            new Feature(11, "Listar Funcionalidades", "listFeatures"),
            new Feature(12, "Criar Funcionalidade", "createFeature"),
            new Feature(13, "Listar Ações", "listActions"),
            new Feature(14, "Criar Ação", "createAction"),
            new Feature(15, "Criar Item", "createItem"),
            new Feature(16, "Listar Itens", "listItens"),
            new Feature(17, "Listar Componentes", "listComponentes"),
            new Feature(18, "Criar Componente", "createComponente"),
            new Feature(19, "Listar Pilares", "listPilares"),
            new Feature(20, "Criar Pilar", "createPilar"),
            new Feature(21, "Listar Ciclos", "listCiclos"),
            new Feature(22, "Criar Ciclo", "createCiclo"),
            new Feature(23, "Listar Entidades", "listEntidades"),
            new Feature(24, "Criar Entidade", "createEntidade"),
            new Feature(25, "Listar Planos", "listPlanos"),
            new Feature(26, "Criar Plano", "createPlano"),
            new Feature(27, "Listar Escritórios", "listEscritorios"),
            new Feature(28, "Criar Escritório", "createEscritorio"),
            new Feature(29, "Listar Tipos de Notas", "listTiposNotas"),
            new Feature(30, "Criar Tipo de Nota", "createTipoNota"),
            new Feature(31, "Designar Equipes", "designarEquipes"),
            new Feature(32, "Distribuir Atividades", "distribuirAtividades"),
            new Feature(33, "Avaliar Planos", "avaliarPlanos"),
            new Feature(34, "Visualizar Matriz", "viewMatriz"),
            new Feature(35, "Home Chefe", "homeChefe"),
            new Feature(36, "Home Supervisor", "homeSupervisor"),
            new Feature(37, "Home Auditor", "homeAuditor"),
            new Feature(38, "Listar Radares", "listRadares"),
            new Feature(39, "Criar Radar", "createRadar"),
            new Feature(40, "Listar Chamados", "listChamados"),
            new Feature(41, "Criar Chamado", "createChamado"),
            new Feature(42, "Listar Versões", "listVersoes"),
            new Feature(43, "Criar Versão", "createVersao"),
            new Feature(44, "Listar Anotações", "listAnotacoes"),
            new Feature(45, "Criar Anotação", "createAnotacao"),
            new Feature(46, "Visualizar Entidade", "viewEntidade")
        };
    
        StringBuilder sb = new StringBuilder();
        for (Feature feature : features) {
            sb.append(String.format(
                "IF NOT EXISTS (SELECT 1 FROM %s.features WHERE id_feature = %d)\n" +
                "BEGIN\n" +
                "    INSERT INTO %s.features (id_feature, name, code)\n" +
                "    VALUES (%d, '%s', '%s');\n" +
                "END;\n\n",
                schema, feature.id,
                schema, feature.id, feature.name.replace("'", "''"), feature.code
            ));
        }
    
        return sb.toString();
    }
    
}
