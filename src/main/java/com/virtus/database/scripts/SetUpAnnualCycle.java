package com.virtus.database.scripts;

import java.util.ArrayList;
import java.util.List;

public class SetUpAnnualCycle {
    // Estruturas auxiliares
    class ItemAux {
        String nome;
        String descricao;
    }

    class ElementoAux {
        String nome;
        String descricao;
        List<ItemAux> itens;
    }

    class TipoNotaAux {
        String nome;
        String descricao;
        List<ElementoAux> elementos;
    }

    class ComponenteAux {
        String nome;
        String descricao;
        List<TipoNotaAux> tiposNotas;
    }

    class PilarAux {
        String nome;
        String descricao;
        List<ComponenteAux> componentes;
    }

    class CicloAux {
        String nome;
        String descricao;
        List<PilarAux> pilares;
    }

    // Parte da montagem do ciclo anual
    public class CicloFactory {

        public CicloAux montarCicloAnual() {
            CicloAux cicloAux = new CicloAux();
            PilarAux pilarRC = new PilarAux();
            PilarAux pilarG = new PilarAux();
            List<PilarAux> pilares = new ArrayList<>();
            List<ComponenteAux> componentes;

            // Pilar Riscos e Controles
            pilarRC.nome = "Riscos e Controles";
            componentes = new ArrayList<>();
            componentes.add(initRiscoAtuarial());
            componentes.add(initRiscoDeCredito());
            componentes.add(initRiscoDeMercado());
            componentes.add(initRiscoDeLiquidez());
            pilarRC.componentes = componentes;

            // Pilar Governança
            pilarG.nome = "Governança";
            componentes = new ArrayList<>();
            componentes.add(initGovernanca());
            pilarG.componentes = componentes;

            pilares.add(pilarRC);
            pilares.add(pilarG);

            cicloAux.nome = "Ciclo Anual 2021";
            cicloAux.pilares = pilares;

            return cicloAux;
        }

        public CicloAux montarCicloBienal() {
            CicloAux cicloAux = new CicloAux();
            PilarAux pilarRC = new PilarAux();
            List<PilarAux> pilares = new ArrayList<>();
            List<ComponenteAux> componentes = new ArrayList<>();

            pilarRC.nome = "Riscos e Controles";
            componentes.add(initRiscoDeCredito());
            componentes.add(initRiscoDeMercado());
            componentes.add(initRiscoDeLiquidez());
            componentes.add(initRiscoAtuarial());
            pilarRC.componentes = componentes;

            pilares.add(pilarRC);

            cicloAux.nome = "Ciclo Bienal 2021-2022";
            cicloAux.pilares = pilares;

            return cicloAux;
        }

        public CicloAux montarCicloTrienal() {
            CicloAux cicloAux = new CicloAux();
            PilarAux pilarRC = new PilarAux();
            List<PilarAux> pilares = new ArrayList<>();
            List<ComponenteAux> componentes = new ArrayList<>();

            pilarRC.nome = "Riscos e Controles";
            componentes.add(initRiscoDeCredito());
            componentes.add(initRiscoDeMercado());
            componentes.add(initRiscoDeLiquidez());
            componentes.add(initRiscoAtuarial());
            pilarRC.componentes = componentes;

            pilares.add(pilarRC);

            cicloAux.nome = "Ciclo Trienal 2021-2023";
            cicloAux.pilares = pilares;

            return cicloAux;
        }

        // Métodos stubs para simular a inicialização
        ComponenteAux initRiscoDeCredito() {
            return new ComponenteAux();
        }

        ComponenteAux initRiscoDeMercado() {
            return new ComponenteAux();
        }

        ComponenteAux initRiscoDeLiquidez() {
            return new ComponenteAux();
        }

        ComponenteAux initRiscoAtuarial() {
            return new ComponenteAux();
        }

        ComponenteAux initGovernanca() {
            return new ComponenteAux();
        }
    }
}
