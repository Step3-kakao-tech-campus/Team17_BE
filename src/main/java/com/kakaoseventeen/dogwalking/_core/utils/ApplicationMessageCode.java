package com.kakaoseventeen.dogwalking._core.utils;

import lombok.Getter;

@Getter
public enum ApplicationMessageCode {

    MEMBER_NOT_FOUND("사용자에 대한 정보가 존재하지 않습니다."),
    NOTIFICATION_NOT_FOUND("일치하는 공고글이 존재하지 않습니다."),
    APPLICATION_NOT_FOUND("존재하지 않는 지원서 입니다."),
    MATCH_NOT_FOUND("잘못된 요청 입니다.");

    private String value;

    ApplicationMessageCode(String value){
        this.value = value;
    }
}
