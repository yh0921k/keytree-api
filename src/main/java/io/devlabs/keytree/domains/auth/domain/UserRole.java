package io.devlabs.keytree.domains.auth.domain;

import lombok.Getter;

@Getter
public enum UserRole {
  MASTER("MASTER", 3),
  ADMIN("ADMIN", 2),
  USER("USER", 1);

  private final String name;
  private final int level;

  UserRole(String name, int level) {
    this.name = name;
    this.level = level;
  }

  public boolean validateLevel(UserRole userRole) {
    return this.level >= userRole.level;
  }
}
