package com.virtus.domain.enums;

import com.virtus.common.enums.BaseEnum;
import com.virtus.translate.Translator;

public enum AverageType implements BaseEnum {

    ARITHMETIC("arithmetic"),
    GEOMETRIC("geometric"),
    HARMONICA("harmonica");

    private final String key;

    AverageType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return Translator.translate(getKey());
    }
}
