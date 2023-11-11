package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

/**
 * ReviewMessageCode : 에러의 메세지 내용들을 제공하는 enum 클래스
 *
 * @author 박영규
 * @version 1.0
 */
@Getter
public enum ReviewMessageCode {

    MEMBER_ID_NOT_EXIST("잘못된 사용자 접근입니다."),
    RECEIVE_MEMBER_ID_NOT_EXIST("리뷰받는 사용자가 존재하지 않습니다."),
    NOTIFICATION_ID_NOT_EXIST("잘못된 요청 입니다."),
    REVIEW_CONTENT_NOT_EXIST("리뷰 내용을 작성해주세요."),
    REVIEW_MEMBER_NOT_FOUND("리뷰받을 사용자를 찾을 수 없습니다.");

    private String value;

    ReviewMessageCode(String value) {
        this.value = value;
    }
}
