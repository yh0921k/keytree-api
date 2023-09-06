package io.devlabs.keytree.domains.auth.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public class Password {
  private final String value;

  public Password(String rawPassword) {
    validatePassword(rawPassword);
    value = rawPassword;
  }

  private void validatePassword(String rawPassword) {
    if (!isPasswordLengthValid(rawPassword)) {
      throw new IllegalArgumentException("패스워드는 8글자 이상이어야 합니다.");
    }

    if (!isPasswordComplexEnough(rawPassword)) {
      throw new IllegalArgumentException("패스워드는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 세 가지 이상으로 구성되어야 합니다.");
    }
  }

  private boolean isPasswordComplexEnough(String rawPassword) {
    int complexity = 0;
    complexity += containsLowerCaseLetter(rawPassword) ? 1 : 0;
    complexity += containsUpperCaseLetter(rawPassword) ? 1 : 0;
    complexity += containsDigit(rawPassword) ? 1 : 0;
    complexity += containsSpecialCharacter(rawPassword) ? 1 : 0;

    return complexity >= 3;
  }

  private boolean isPasswordLengthValid(String rawPassword) {
    return rawPassword.length() >= 8;
  }

  private boolean containsLowerCaseLetter(String rawPassword) {
    return !rawPassword.equals(rawPassword.toUpperCase());
  }

  private boolean containsUpperCaseLetter(String rawPassword) {
    return !rawPassword.equals(rawPassword.toLowerCase());
  }

  private boolean containsDigit(String rawPassword) {
    return rawPassword.matches(".*\\d.*");
  }

  private boolean containsSpecialCharacter(String rawPassword) {
    Pattern pattern = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]+");
    Matcher matcher = pattern.matcher(rawPassword);
    return matcher.find();
  }
}
