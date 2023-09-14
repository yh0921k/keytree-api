package io.devlabs.keytree.domains.auth.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordUtils {

  private final PasswordEncoder passwordEncoder;

  public String encrypt(String rawPassword) {
    return passwordEncoder.encode(rawPassword);
  }

  public void validatePassword(String rawPassword, String encodedPassword) {
    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
    }
  }
}
