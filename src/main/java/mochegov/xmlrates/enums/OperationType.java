package mochegov.xmlrates.enums;

import lombok.Getter;

@Getter
public enum OperationType {
    PAYMENT_STATE_DUTY("Оплата госпошлин и штрафов"),
    RECEIVE_PAYMENT("Получение стипендий, пособий и других выплат");

    private final String name;

    OperationType(String name) {
        this.name = name;
    }
}
