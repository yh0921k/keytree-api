package io.devlabs.keytree.domains.company.domain;

public enum UserRole {
    MASTER("MASTER"),
    ADMIN("ADMIN"),
    USER("USER");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}