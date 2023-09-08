package io.devlabs.keytree.domains.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberTest {

    @ParameterizedTest
    @ValueSource(strings = {"01012345678", "0111231234", "0102345567"})
    @DisplayName("전화번호 형식이 정상적인 경우 PhoneNumber 인스턴스 생성됨")
    public void createValidPhoneNumber(String phoneNumber) {
        // when
        PhoneNumber result = new PhoneNumber(phoneNumber);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getValue()).isEqualTo(phoneNumber);
    }

    @ParameterizedTest
    @ValueSource(strings = {"010012341234", "010123441234", "012341234"})
    @DisplayName("전화번호 형식이 정상적이지 아닌 경우 예외 발생")
    public void isPhoneNumberValid(String phoneNumber) {
        assertThatThrownBy(() -> new PhoneNumber(phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("휴대폰 번호 형식을 확인하세요.");
    }
}
