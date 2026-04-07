package br.com.samp.financemanager.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TransactionStatus {
    PLANNED("planned"),
    PENDING_CONFIRMATION("pending_confirmation"),
    CONFIRMED("confirmed"),
    DELETED("deleted");

    private final String name;

    TransactionStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
