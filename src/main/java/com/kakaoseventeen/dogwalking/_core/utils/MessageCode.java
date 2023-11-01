package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

@Getter
public enum MessageCode {

    WALK_NOT_EXIST("해당 산책이 존재하지 않습니다."),
    MATCH_NOT_EXIST("해당 매칭이 존재하지 않습니다."),


    NOTIFICATION_NOT_EXIST("해당 공고글이 존재하지 않습니다.");


    private final String value;

    MessageCode(String value) {
        this.value = value;
    }
}