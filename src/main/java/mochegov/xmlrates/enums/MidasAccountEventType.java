package mochegov.xmlrates.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum MidasAccountEventType {
    MIDAS_ACCOUNT_OPEN_EVENT("Account_Open"),
    MIDAS_ACCOUNT_CLOSE_EVENT("Account_Close"),
    MIDAS_ACCOUNT_CHANGE_CBA("Chg_CBA");

    private final String name;

    MidasAccountEventType(String name) {
        this.name = name;
    }

    public static Optional<MidasAccountEventType> getByName(String name) {
        return Arrays.stream(MidasAccountEventType.values())
            .filter(eventType -> eventType.getName().equals(name))
            .findFirst();
    }
}