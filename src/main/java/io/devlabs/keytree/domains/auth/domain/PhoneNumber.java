package io.devlabs.keytree.domains.auth.domain;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class PhoneNumber {
    private final String value;

    public PhoneNumber(String phoneNumber) {
            validatePhoneNumber(phoneNumber);
        value = phoneNumber;
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!isValidatePhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("휴대폰 번호 형식을 확인하세요.");
        }
    }

    private boolean isValidatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^(01[016789]{1})[0-9]{3,4}[0-9]{4}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.find();
    }
}
