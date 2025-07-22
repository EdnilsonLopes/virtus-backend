package com.virtus.database.scripts;

import java.util.ArrayList;
import java.util.List;

public class MembersScripts {
    /**
     * Classe interna para representar a associação entre um usuário e um
     * escritório.
     */
    static class Membro {
        String username;
        String abreviaturaEscritorio;

        public Membro(String username, String abreviaturaEscritorio) {
            this.username = username;
            this.abreviaturaEscritorio = abreviaturaEscritorio;
        }
    }

    /**
     * Gera uma lista de strings SQL para inserir os membros na base de dados.
     * Cada string na lista é um comando INSERT completo para um membro.
     * 
     * @param schema O nome do schema do banco de dados.
     * @return Uma lista de strings SQL.
     */
    public static String insertMembersIfNotExists(String schema) {
        // Array com todos os membros a serem inseridos
        Membro[] membros = {
                new Membro("alessandro.vaine", "ERSP"),
                new Membro("alexandre", "ERPE"),
                new Membro("alfredo", "ERRS"),
                new Membro("alvaro", "ERRJ"),
                new Membro("andre.silva", "ERRJ"),
                new Membro("andre.goncalves", "ERRJ"),
                new Membro("angelica.campinho", "ERRJ"),
                new Membro("annette", "ERRJ"),
                new Membro("antonio.portes", "ERMG"),
                new Membro("antonio.garcia", "ERDF"),
                new Membro("antonio.frainer", "ERRS"),
                new Membro("arnaldo", "ERDF"),
                new Membro("carlos.buccos", "ERRJ"),
                new Membro("carlos.neves", "ERRS"),
                new Membro("carlos", "ERDF"),
                new Membro("charles.diniz", "ERRS"),
                new Membro("charles.dantas", "ERDF"),
                new Membro("chow", "ERSP"),
                new Membro("clovis.coelho", "ERSP"),
                new Membro("dagomar", "ERDF"),
                new Membro("dauto", "ERSP"),
                new Membro("david.coutinho", "ERMG"),
                new Membro("delma", "ERDF"),
                new Membro("diogo.araujo", "ERSP"),
                new Membro("douglas", "ERMG"),
                new Membro("eduardo.meireles", "ERRJ"),
                new Membro("elianeoliveira.costa", "ERRJ"),
                new Membro("elyson", "ERSP"),
                new Membro("enaide", "ERPE"),
                new Membro("estevam.brayn", "ERSP"),
                new Membro("evelyn", "ERSP"),
                new Membro("felipe", "ERDF"),
                new Membro("francisco.arruda", "ERPE"),
                new Membro("francisco.junior", "ERDF"),
                new Membro("germano.barreira", "ERRJ"),
                new Membro("giselle", "ERMG"),
                new Membro("hamilton", "ERDF"),
                new Membro("helvio", "ERDF"),
                new Membro("hilton", "ERDF"),
                new Membro("humberto", "ERRJ"),
                new Membro("isabel.cmaia", "ERSP"),
                new Membro("izabel", "ERDF"),
                new Membro("james", "ERDF"),
                new Membro("jorge", "ERDF"),
                new Membro("jose.chedeak", "ERDF"),
                new Membro("jose.pires", "ERRS"),
                new Membro("jose.cestari", "ERRS"),
                new Membro("jose.fernanes", "ERMG"),
                new Membro("jucinea", "ERDF"),
                new Membro("juliana.pereira", "ERDF"),
                new Membro("leonardo.alves", "ERDF"),
                new Membro("luciano.draghetti", "ERRS"),
                new Membro("luciano.pinheiro", "ERDF"),
                new Membro("luis.pugliese", "ERSP"),
                new Membro("luis.barbosa", "ERRJ"),
                new Membro("luis.angoti", "ERDF"),
                new Membro("luiz", "ERRJ"),
                new Membro("maique", "ERDF"),
                new Membro("marcelo.melo", "ERSP"),
                new Membro("marcelo.wajsenzon", "ERRJ"),
                new Membro("marcia.vivas", "ERRJ"),
                new Membro("marcio", "ERSP"),
                new Membro("marcus", "ERRJ"),
                new Membro("maria", "ERSP"),
                new Membro("maria.fpimenta", "ERMG"),
                new Membro("maria.cherulli", "ERRJ"),
                new Membro("marina", "ERDF"),
                new Membro("mauricio.nakata", "ERDF"),
                new Membro("mauricio.lundgren", "ERPE"),
                new Membro("maury.oliveira", "ERRJ"),
                new Membro("nercilia", "ERRJ"),
                new Membro("nivea", "ERDF"),
                new Membro("otavio.reis", "ERPE"),
                new Membro("patricia", "ERRJ"),
                new Membro("paulo.matsumoto", "ERSP"),
                new Membro("paulo.diniz", "ERSP"),
                new Membro("paulo.vitorino", "ERDF"),
                new Membro("pedro", "ERSP"),
                new Membro("pedro.pauloeugenio", "ERRJ"),
                new Membro("peterson.goncalves", "ERSP"),
                new Membro("rafael", "ERRS"),
                new Membro("raquel.gerhardt", "ERRS"),
                new Membro("rita", "ERDF"),
                new Membro("roberto.sakamoto", "ERSP"),
                new Membro("rodrigo.oliveira", "ERRJ"),
                new Membro("rodrigo.abreu", "ERRJ"),
                new Membro("rodrigo.andrade", "ERSP"),
                new Membro("romulo", "ERSP"),
                new Membro("sergio", "ERDF"),
                new Membro("simone", "ERRJ"),
                new Membro("vandeisa", "ERMG"),
                new Membro("vanessa", "ERRS"),
                new Membro("veronica", "ERDF"),
                new Membro("vitor", "ERDF"),
                new Membro("walter", "ERPE"),
                new Membro("wander.mingardi", "ERSP"),
                new Membro("wania.capparelli", "ERMG"),
                new Membro("wellington.marques", "ERDF"),
                new Membro("wellington.pereira", "ERMG")
        };

        String insertTemplate = "INSERT INTO %s.membros (id_membro, id_usuario, id_escritorio) " +
                "SELECT %d, a.id_user, b.id_escritorio FROM %s.users a, %s.escritorios b " +
                "WHERE b.abreviatura = '%s' AND a.username = '%s' AND " +
                "NOT EXISTS (SELECT 1 FROM %s.membros c WHERE c.id_usuario = a.id_user AND c.id_escritorio = b.id_escritorio)";

        StringBuilder sqlBuilder = new StringBuilder();

        for (int i = 0; i < membros.length; i++) {
            Membro m = membros[i];
            // Usando o índice do loop + 1 como a chave primária (PK)
            int idMembro = i + 1;
            sqlBuilder.append(String.format(insertTemplate,
                    schema,
                    idMembro,
                    schema,
                    schema,
                    m.abreviaturaEscritorio,
                    m.username,
                    schema));
        }

        return sqlBuilder.toString();
    }
}
