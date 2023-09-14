package io.devlabs.keytree.domains.auth.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PasswordUtilsTest {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("문자열 BCrypt 암호화 검증")
  void encrypt() {
    // given
    String rawPassword = "Test1234!";

    // when
    String encryptedPassword = passwordEncoder.encode(rawPassword);
    boolean matches = passwordEncoder.matches(rawPassword, encryptedPassword);

    // then
    assertThat(matches).isTrue();
  }
}
