package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

@Getter
public enum MessageCode {

    WALK_NOT_EXIST("해당 산책이 존재하지 않습니다."),
    MATCH_NOT_EXIST("해당 매칭이 존재하지 않습니다."),

    MEMBER_NOT_EXIST("이메일 또는 비밀번호를 다시 확인해주세요."),

    EXPIRED_TOKEN("만료된 토큰입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    ACCESS_DENIED("접근 권한이 없습니다."),
    NON_LOGIN("인증이 필요합니다.");

    private final String value;

    MessageCode(String value) {
        this.value = value;
    }
}