package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

/**
 * MessageCode : 에러의 메세지 내용들을 제공하는 enum 클래스
 *
 * @author 승건 이
 * @version 1.0
 */
@Getter
public enum MessageCode {

    WALK_NOT_EXIST("해당 산책이 존재하지 않습니다."),
    MATCH_NOT_EXIST("해당 매칭이 존재하지 않습니다."),

    DOG_LIST_NOT_EXIST("해당 강아지가 존재하지 않습니다."),
    DOG_NOT_EXIST("등록된 강아지가 아닙니다."),
    MUNG_COIN_NOT_ENOUGH("멍코인이 부족합니다."),
    NOTIFICATION_FORBIDDEN("수정 권한이 없습니다."),
    NOTIFICATION_TIME_ERROR("시간을 다시 확인해주세요."),
    NOTIFICATION_NOT_EXIST("해당 공고글이 존재하지 않습니다."),
    DUPLICATE_NOTIFICATION("해당 공고는 이미 산책과 연결되어있습니다."),

    PAYMENT_NOT_EXIST("해당 결제가 존재하지 않습니다."),

    IMAGE_NOT_EXIST("해당 이미지가 존재하지 않습니다.");


    private final String value;

    MessageCode(String value) {
        this.value = value;
    }
}