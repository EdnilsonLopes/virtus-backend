package com.virtus.domain.enums;

import com.virtus.common.enums.BaseEnum;

public enum EntityInSystem implements BaseEnum {
    itemAAvaliar("itemToEvaluate"),
    elemento("element"),
    elemento_avaliacao("elementInTheAssessment"),
    entidade("entity"),
    chamado("call"),
    ciclo("cycle"),
    componente("component"),
    matriz("matrix"),
    plano("plan"),
    usuario("user");

    private final String key;

    EntityInSystem(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
