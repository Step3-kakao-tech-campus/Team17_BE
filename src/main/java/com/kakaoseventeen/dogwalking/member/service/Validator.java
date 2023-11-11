package com.kakaoseventeen.dogwalking.member.service;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Validator : 이메일, 비밀번호 유효성을 검증하는 클래스
 *
 * @author 박영규
 * @version 1.0
 */
@Component
public class Validator {

    private final String EMAIL_REGEX ="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; // RFC-5322
    private final String PASSWORD_LENGTH_REGEX = "^.{8,16}$"; // 비밀번호 길이 8~16
    private final String PASSWORD_PATTERN_REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]+$"; // 특수문자, 영문자, 숫자

    /**
     * 이메일 형식 검증 메서드
     */
    public boolean validEmailFormat(String email) {
        return Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();
    }

    /**
     * 비밀번호 형식 검증 메서드
     */
    public boolean validPasswordFormat(String password) {
        return Pattern.compile(PASSWORD_PATTERN_REGEX)
                .matcher(password)
                .matches();
    }

    /**
     * 비밀번호 길이 검증 메서드
     */
    public boolean checkPasswordLength(String password) {
        return Pattern.compile(PASSWORD_LENGTH_REGEX)
                .matcher(password)
                .matches();
    }
}

