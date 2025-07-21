package com.virtus.database.scripts;

public class OfficesScripts {
    public static String insertEscritoriosIfNotExists(String schema) {
        class Escritorio {
            int idEscritorio;
            String nome;
            String descricao;
            String abreviatura;

            Escritorio(int idEscritorio, String nome, String descricao, String abreviatura) {
                this.idEscritorio = idEscritorio;
                this.nome = nome;
                this.descricao = descricao;
                this.abreviatura = abreviatura;
            }
        }

        Escritorio[] escritorios = new Escritorio[] {
                new Escritorio(6, "Escritório de Representação - Distrito Federal",
                        "Escritório de Representação - Distrito Federal", "ERDF"),
                new Escritorio(3, "Escritório de Representação - Minas Gerais",
                        "Escritório de Representação - Minas Gerais", "ERMG"),
                new Escritorio(1, "Escritório de Representação - Pernambuco",
                        "Escritório de Representação - Pernambuco", "ERPE"),
                new Escritorio(5, "Escritório de Representação - Rio de Janeiro",
                        "Escritório de Representação - Rio de Janeiro", "ERRJ"),
                new Escritorio(4, "Escritório de Representação - Rio Grande do Sul",
                        "Escritório de Representação - Rio Grande do Sul", "ERRS"),
                new Escritorio(2, "Escritório de Representação - São Paulo", "Escritório de Representação - São Paulo",
                        "ERSP")
        };

        StringBuilder sb = new StringBuilder();
        for (Escritorio escritorio : escritorios) {
            sb.append(String.format(
                    "IF NOT EXISTS (SELECT 1 FROM %s.escritorios WHERE id_escritorio = %d)\n" +
                            "BEGIN\n" +
                            "    INSERT INTO %s.escritorios (id_escritorio, nome, descricao, abreviatura, id_author, criado_em)\n"
                            +
                            "    VALUES (%d, '%s', '%s', '%s', 1, GETDATE());\n" +
                            "END;\n\n",
                    schema, escritorio.idEscritorio,
                    schema, escritorio.idEscritorio,
                    escritorio.nome.replace("'", "''"),
                    escritorio.descricao.replace("'", "''"),
                    escritorio.abreviatura.replace("'", "''")));
        }

        return sb.toString();
    }

}
