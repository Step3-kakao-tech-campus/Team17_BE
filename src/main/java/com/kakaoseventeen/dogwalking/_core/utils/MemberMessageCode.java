package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

@Getter
public enum MemberMessageCode {
    INVALID_EMAIL_FORMAT("아이디는 이메일 형식으로 작성해야 합니다."),
    INVALID_PASSWORD_LENGTH("비밀번호는 8~16자로 작성해야 합니다."),
    INVALID_PASSWORD_FORMAT("비밀번호는 특수문자, 영문자, 숫자가 포함되어야 합니다."),
    MEMBER_NOT_EXIST("회원가입이 안된 유저입니다."),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다."),
    REQUIRES_AUTHENTICATION("인증되지 않은 유저입니다."),
    FORBIDDEN_MEMBER("권한이 없는 유저입니다."),
    DUPLICATE_EMAIL("중복된 이메일 입니다.");



    private final String value;

    MemberMessageCode(String value) {
        this.value = value;
    }
}
