package io.devlabs.keytree.domains.company.domain;

public enum UserRole {
    MASTER("MASTER"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER")
    ;

    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}