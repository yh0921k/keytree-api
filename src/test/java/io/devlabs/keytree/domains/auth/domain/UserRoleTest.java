package io.devlabs.keytree.domains.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UserRoleTest {

  @ParameterizedTest
  @CsvSource(
      value = {
        "USER:USER:true",
        "USER:ADMIN:false",
        "USER:MASTER:false",
        "ADMIN:USER:true",
        "ADMIN:ADMIN:true",
        "ADMIN:MASTER:false",
        "MASTER:USER:true",
        "MASTER:ADMIN:true",
        "MASTER:MASTER:true"
      },
      delimiter = ':')
  @DisplayName("사용자 권한 유효성 검증")
  void validateLevel(UserRole userRole, UserRole targetRole, boolean expected) {
    // when
    boolean isValidRole = userRole.validateLevel(targetRole);

    // then
    assertThat(isValidRole).isEqualTo(expected);
  }
}
