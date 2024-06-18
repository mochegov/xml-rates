package mochegov.xmlrates.enums;

import lombok.Getter;

@Getter
public enum ResidentSign {
    RESIDENT("Резидент"),
    NON_RESIDENT("Не резидент");

    private final String name;

    ResidentSign(String name) {
        this.name = name;
    }
}
