package br.com.samp.financemanager.model.enums;

import lombok.Getter;

@Getter
public enum UserRoles {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    UserRoles(String role) {
        this.role = role;
    }
}
