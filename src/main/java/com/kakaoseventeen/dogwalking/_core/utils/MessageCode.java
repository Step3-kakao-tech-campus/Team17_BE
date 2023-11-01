package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

@Getter
public enum MessageCode {

    WALK_NOT_EXIST("해당 산책이 존재하지 않습니다."),
    MATCH_NOT_EXIST("해당 매칭이 존재하지 않습니다."),
    MemberNotExistException("해당 유저가 존재하지 않습니다."),
    DOG_LIST_NOT_EXIST("해당 강아지가 존재하지 않습니다."),
    DOG_NOT_EXIST("등록된 강아지가 아닙니다."),
    MUNG_COIN_NOT_ENOUGH("멍코인이 부족합니다."),
    NOTIFICATION_FORBIDDEN("수정 권한이 없습니다."),

    NOTIFICATION_NOT_EXIST("해당 공고글이 존재하지 않습니다.");


    private final String value;

    MessageCode(String value) {
        this.value = value;
    }
}