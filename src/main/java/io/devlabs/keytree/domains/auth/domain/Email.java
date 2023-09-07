package io.devlabs.keytree.domains.auth.domain;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class Email {
    private final String value;

    public Email(String email) {
        validateEmail(email);
        value = email;
    }

    private void validateEmail(String email) {
        if (!isValidateFormEmail(email)) {
            throw new IllegalArgumentException("이메일 형식을 확인해주세요.");
        }
    }

    private boolean isValidateFormEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-z]+\\.[a-z]{2,3}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}
