package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

@Getter
public enum ReviewMessageCode {

    MEMBER_ID_NOT_EXIST("잘못된 사용자 접근입니다."),
    RECEIVE_MEMBER_ID_NOT_EXIST("리뷰받는 사용자가 존재하지 않습니다."),
    NOTIFICATION_ID_NOT_EXIST("잘못된 요청 입니다."),
    REVIEW_CONTENT_NOT_EXIST("리뷰 내용을 작성해주세요.");

    private String value;

    ReviewMessageCode(String value) {
        this.value = value;
    }
}
