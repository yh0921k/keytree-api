package io.devlabs.keytree.domains.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.util.ReflectionTestUtils.invokeMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PasswordTest {

  Password javaReflectionPassword = new Password("Test1234!");

  @InjectMocks Password springReflectionPassword = new Password("Test1234!");

  @ParameterizedTest
  @ValueSource(strings = {"a234567*", "A234567*", "A234567b"})
  @DisplayName("패스워드가 정상적인 경우 Password 인스턴스가 생성됨")
  public void createValidPassword(String rawPassword) {
    // when
    Password password = new Password(rawPassword);

    // then
    assertThat(password).isNotNull();
    assertThat(password.getValue()).isEqualTo(rawPassword);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "1234", "1234567"})
  @DisplayName("패스워드가 8글자 이상이 아닌 경우 예외 발생")
  public void isPasswordValidLength(String rawPassword) {
    assertThatThrownBy(() -> new Password(rawPassword))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("패스워드는 8글자 이상이어야 합니다.");
  }

  @ParameterizedTest
  @ValueSource(strings = {"12345678", "1234567a", "1234567A", "1234567*"})
  @DisplayName("패스워드가 문자 종류 중 세 가지 이상으로 구성되어있지 않은 경우 예외 발생")
  public void isPasswordComplexEnough(String rawPassword) {
    assertThatThrownBy(() -> new Password(rawPassword))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("패스워드는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 세 가지 이상으로 구성되어야 합니다.");
  }

  // 아래는 접근 제한 메서드에 대한 테스트 예시(Reflection API)
  @ParameterizedTest
  @CsvSource(
      value = {"1234567:false", "12345678:true"},
      delimiter = ':')
  @DisplayName("패스워드가 8자리 이상인지 검증")
  public void isPasswordLengthValid(String rawPassword, boolean expected)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    // given
    Method isPasswordLengthValid =
        Password.class.getDeclaredMethod("isPasswordLengthValid", String.class);
    isPasswordLengthValid.setAccessible(true);

    // when
    boolean result = (boolean) isPasswordLengthValid.invoke(javaReflectionPassword, rawPassword);

    // then
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(
      value = {"AAAAAAAA:false", "AAAAAAAb:true"},
      delimiter = ':')
  @DisplayName("소문자 포함 검증")
  public void containsLowerCaseLetter(String rawPassword, boolean expected)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    // given
    Method isPasswordLengthValid =
        Password.class.getDeclaredMethod("containsLowerCaseLetter", String.class);
    isPasswordLengthValid.setAccessible(true);

    // when
    boolean result = (boolean) isPasswordLengthValid.invoke(javaReflectionPassword, rawPassword);

    // then
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(
      value = {"aaaaaaaa:false", "aaaaaaaB:true"},
      delimiter = ':')
  @DisplayName("대문자 포함 검증")
  public void containsUpperCaseLetter(String rawPassword, boolean expected)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    // given
    Method isPasswordLengthValid =
        Password.class.getDeclaredMethod("containsUpperCaseLetter", String.class);
    isPasswordLengthValid.setAccessible(true);

    // when
    boolean result = (boolean) isPasswordLengthValid.invoke(javaReflectionPassword, rawPassword);

    // then
    assertThat(result).isEqualTo(expected);
  }

  // 아래는 접근 제한 메서드에 대한 테스트 예시(Spring ReflectionTestUtils API)
  @ParameterizedTest
  @CsvSource(
      value = {"aaaaaaaa:false", "aaaaaaa1:true"},
      delimiter = ':')
  @DisplayName("숫자 포함 검증")
  public void containsDigit(String rawPassword, boolean expected) {
    // when
    boolean result = invokeMethod(springReflectionPassword, "containsDigit", rawPassword);

    // then
    assertThat(result).isEqualTo(expected);
  }

  @ParameterizedTest
  @CsvSource(
      value = {"aaaaaaaa:false", "aaaaaaa*:true"},
      delimiter = ':')
  @DisplayName("특수문자 포함 검증")
  public void containsSpecialCharacter(String rawPassword, boolean expected) {
    // when
    boolean result =
        invokeMethod(springReflectionPassword, "containsSpecialCharacter", rawPassword);

    // then
    assertThat(result).isEqualTo(expected);
  }
}
