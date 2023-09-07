package io.devlabs.keytree.domains.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {"abcd123efg@gmail.com", "Abcdefg123@naver.com", "abcd123EFG@daum.net"})
    @DisplayName("이메일 형식이 정상적인 경우에 Email 인스턴스 생성됨")
    public void createValidEmail(String emailAddress) {
        // when
        Email email = new Email(emailAddress);

        // then
        assertThat(email).isNotNull();
        assertThat(email.getValue()).isEqualTo(emailAddress);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefg123@Gmail.com", "abcdefg123@naver.1om", "abcdefg123!@daum.net", "abcdefg123@daum.n", "abcdefg123@daum.Net", "abcdefg123@daum.nett"})
    @DisplayName("이메일 형식이 정상적이지 않은 경우 예외 발생")
    public void isEmailFormValid(String emailAddress) {
        System.out.println("emailAddress = " + emailAddress);
        assertThatThrownBy(() -> new Email(emailAddress))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식을 확인해주세요.");
    }
}
